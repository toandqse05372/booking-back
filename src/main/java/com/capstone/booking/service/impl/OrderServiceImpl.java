package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.OrderConverter;
import com.capstone.booking.common.converter.OrderItemConverter;
import com.capstone.booking.common.helper.pdf.PdfPrinter;
import com.capstone.booking.common.helper.pdf.PrintRequest;
import com.capstone.booking.common.key.OrderStatus;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.entity.dto.OrderItemDTO;
import com.capstone.booking.repository.*;
import com.capstone.booking.service.OrderService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemConverter orderItemConverter;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PdfPrinter pdfPrinter;

    @Autowired
    public JavaMailSender emailSender;

    //create order
    @Override
    public ResponseEntity<?> create(OrderDTO orderDTO, OrderStatus status) {
        Order order = orderConverter.toOrder(orderDTO);

        User user = userRepository.findById(orderDTO.getUserId()).get();
        order.setUser(user);

        Order newestOrder = orderRepository.findTopByOrderByIdDesc();
        if (newestOrder != null) {
            order.setOrderCode("ORDER" + (newestOrder.getId() + 1));
        } else {
            order.setOrderCode("ORDER" + 1);
        }
        order.setStatus(status.toString());
        Order saved = orderRepository.save(order);
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO dto : orderDTO.getOrderItems()) {
            OrderItem orderItem = orderItemConverter.toItem(dto);
            orderItem.setOrder(saved);
            orderItems.add(orderItem);
        }
        orderItemRepository.saveAll(orderItems);
        return ResponseEntity.ok(orderConverter.toDTO(order));
    }

    //not use
    @Override
    public ResponseEntity<?> update(OrderDTO orderDTO) {
        Order order = new Order();
        Order oldOrder = orderRepository.findById(orderDTO.getId()).get();
        order = orderConverter.toOrder(orderDTO, oldOrder);

        User user = userRepository.findById(orderDTO.getUserId()).get();
        order.setUser(user);

        orderRepository.save(order);
        return ResponseEntity.ok(orderConverter.toDTO(order));
    }

    //delete order
    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        if (!orderRepository.findById(id).isPresent()) {
            return new ResponseEntity("ORDER_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        orderItemRepository.deleteAllByOrder(orderRepository.findById(id).get());
        orderRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

    //find order by status
    @Override
    public ResponseEntity<?> findByStatus(String status, String code) {
        Output results = orderRepository.findByStatus(status, code);
        return ResponseEntity.ok(results);
    }

    @Override
    public ResponseEntity<?> findByOrderId(Long id) {
        Order order = orderRepository.findById(id).get();
        return ResponseEntity.ok(orderConverter.toDTO(order));
    }

    //send ticket
    @Override
    @Transactional
    public ResponseEntity<?> sendTicket(long id) throws DocumentException, IOException, URISyntaxException, MessagingException {
        Order order = orderRepository.findById(id).get();
        if (!order.getRedemptionDate().after(new Date())) {
            order.setStatus(OrderStatus.EXPIRED.toString());
            return new ResponseEntity("ORDER_EXPIRED", HttpStatus.BAD_REQUEST);
        }
        Set<OrderItem> orderItems = order.getOrderItem();
        TicketType ticketType = ticketTypeRepository.findById(order.getTicketTypeId()).get();
        Place place = placeRepository.findById(ticketType.getPlaceId()).get();
        List<PrintRequest> printRequests = new ArrayList<>();
        // create tickets for each order item
        for (OrderItem item : orderItems) {
            VisitorType type = item.getVisitorType();
            List<Code> codeToUse = codeRepository.findByVisitorTypeIdLimitTo(item.getQuantity(), type);
            // check if number of code remaining in db is enough
            if (codeToUse.size() < item.getQuantity()) {
                return new ResponseEntity("CODE_NOT_ENOUGH", HttpStatus.BAD_REQUEST);
            }
            //create ticket
            List<Ticket> ticketOrder = new ArrayList<>();
            for (int i = 0; i < item.getQuantity(); i++) {
                Ticket ticket = new Ticket();
                ticket.setCode(codeToUse.get(i).getCode());
                ticket.setRedemptionDate(order.getRedemptionDate());
                ticket.setOrderItem(item);
                ticket.setVisitorTypeId(type.getId());
                ticketOrder.add(ticket);
            }
            ticketRepository.saveAll(ticketOrder);
            codeRepository.deleteAll(codeToUse);
            PrintRequest printRequest = new PrintRequest();
            printRequest.setTickets(ticketOrder);
            printRequest.setVisitorType(type);
            printRequest.setTicketType(ticketType);
            printRequest.setPlace(place);
            printRequests.add(printRequest);
            printRequest.setRedemptionDate(order.getRedemptionDate());
        }
        // create file pdf
        File file = pdfPrinter.printPDF(printRequests);
        order.setStatus(OrderStatus.SENT.toString());
        orderRepository.save(order);
        sendEmail(order, file);
        return ResponseEntity.ok(orderConverter.toDTO(order));
    }

    //resent ticket
    @Override
    public ResponseEntity<?> resendTicket(long orderId) throws IOException, MessagingException, URISyntaxException, DocumentException {
        Order order = orderRepository.findById(orderId).get();
        if (!order.getRedemptionDate().after(new Date())) {
            order.setStatus(OrderStatus.EXPIRED.toString());
            return new ResponseEntity("ORDER_EXPIRED", HttpStatus.BAD_REQUEST);
        }
        Set<OrderItem> orderItems = order.getOrderItem();
        TicketType ticketType = ticketTypeRepository.findById(order.getTicketTypeId()).get();
        Place place = placeRepository.findById(ticketType.getPlaceId()).get();
        List<PrintRequest> printRequests = new ArrayList<>();
        for (OrderItem item : orderItems) {
            VisitorType type = item.getVisitorType();
            List<Ticket> ticketCreated = ticketRepository.findAllByOrderItem(item);
            PrintRequest printRequest = new PrintRequest();
            printRequest.setTickets(ticketCreated);
            printRequest.setVisitorType(type);
            printRequest.setTicketType(ticketType);
            printRequest.setPlace(place);
            printRequests.add(printRequest);
            printRequest.setRedemptionDate(order.getRedemptionDate());
        }
        File file = pdfPrinter.printPDF(printRequests);
        order.setStatus(OrderStatus.SENT.toString());
        orderRepository.save(order);
        sendEmail(order, file);
        return ResponseEntity.ok(orderConverter.toDTO(order));
    }

    // send ticket.pdf file to user
    public void sendEmail(Order order, File file) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);

        helper.setTo(order.getMail());
        helper.setSubject("Đơn hàng mã #" + order.getOrderCode());

        helper.setText("Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi. Để sử dụng," +
                " hãy xuất trình vé này tại địa điểm đã đặt chỗ");
        String path1 = file.getPath();

        // Attachment 1
        FileSystemResource file1 = new FileSystemResource(new File(path1));
        helper.addAttachment(file.getPath(), file1);
        emailSender.send(message);
    }

}
