package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.OrderConverter;
import com.capstone.booking.common.converter.OrderItemConverter;
import com.capstone.booking.common.converter.PlaceConverter;
import com.capstone.booking.common.helper.pdf.PdfPrinter;
import com.capstone.booking.common.helper.pdf.PrintRequest;
import com.capstone.booking.common.key.OrderStatus;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.entity.dto.OrderItemDTO;
import com.capstone.booking.entity.dto.lite.PlaceDTOLite;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
    private PlaceConverter placeConverter;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private RemainingRepository remainingRepository;

    @Autowired
    private OrderTokenRepository orderTokenRepository;

    @Autowired
    private PaymentIntentRepository paymentIntentRepository;

    @Autowired
    private PdfPrinter pdfPrinter;

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private ImagePlaceRepository imagePlaceRepository;

    //create order
    @Override
    public ResponseEntity<?> create(OrderDTO orderDTO, OrderStatus status, String paymentIntentId) {
        for (OrderItemDTO dto : orderDTO.getOrderItems()) {
            Remaining remaining = remainingRepository.findByRedemptionDateAndVisitorTypeId(returnToMidnight(orderDTO.getRedemptionDate()),
                    dto.getVisitorTypeId());
            if ((remaining.getTotal() - dto.getQuantity()) < 0) {
                return new ResponseEntity("NOT_ENOUGH", HttpStatus.BAD_REQUEST);
            }
        }
        Order order = orderConverter.toOrder(orderDTO);
        Optional<User> optionalUser = userRepository.findById(orderDTO.getUserId());
        User user = optionalUser.get();
        order.setUser(user);
        Order newestOrder = orderRepository.findTopByOrderByIdDesc();
        if (newestOrder != null) {
            order.setOrderCode("ORDER" + (newestOrder.getId() + 1));
        } else {
            order.setOrderCode("ORDER" + 1);
        }
        order.setStatus(status.toString());
        order.setRedemptionDate(returnToMidnight(orderDTO.getRedemptionDate()));
        try {
            Order saved = orderRepository.save(order);
            List<OrderItem> orderItems = new ArrayList<>();
            for (OrderItemDTO dto : orderDTO.getOrderItems()) {
                OrderItem orderItem = orderItemConverter.toItem(dto);
                orderItem.setOrder(saved);
                orderItems.add(orderItem);
                Remaining remaining = remainingRepository.findByRedemptionDateAndVisitorTypeId(returnToMidnight(order.getRedemptionDate()),
                        dto.getVisitorTypeId());
                remaining.setTotal(remaining.getTotal() - dto.getQuantity());
                remainingRepository.save(remaining);
            }
            orderItemRepository.saveAll(orderItems);

            if (status.equals(OrderStatus.UNPAID)) {
                OrderToken token = new OrderToken();
                token.setOrderId(saved.getId());
                orderTokenRepository.save(token);
            }
            createPaymentIntent(paymentIntentId, saved.getId());
            OrderDTO dto = orderConverter.toDTO(order);
            dto.setPlace(getPlaceLite(order.getPlaceId()));
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return new ResponseEntity("SPAM_REQUEST", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> update(OrderDTO orderDTO, OrderStatus status, String paymentIntentId) {
        Order order = new Order();
        Order oldOrder = orderRepository.findById(orderDTO.getId()).get();
        order = orderConverter.toOrder(orderDTO, oldOrder);
        order.setRedemptionDate(returnToMidnight(orderDTO.getRedemptionDate()));
        order.setStatus(status.toString());
        orderRepository.save(order);
        createPaymentIntent(paymentIntentId, order.getId());
        OrderDTO dto = orderConverter.toDTO(order);
        dto.setPlace(getPlaceLite(order.getPlaceId()));
        return ResponseEntity.ok(dto);
    }

    private void createPaymentIntent(String paymentIntentId, long oid){
        if(paymentIntentId != null){
            PaymentIntent paymentIntent = new PaymentIntent();
            paymentIntent.setOid(oid);
            paymentIntent.setPiId(paymentIntentId);
            paymentIntentRepository.save(paymentIntent);
        }
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
    public ResponseEntity<?> findByStatus(String status, String code, Long placeId, Long page, Long limit) {
        Output results = orderRepository.findByStatus(status, code, placeId, page, limit);
        return ResponseEntity.ok(results);
    }

    @Override
    public ResponseEntity<?> findByOrderId(Long id, Long uid) {
        Order order = orderRepository.findById(id).get();
        if (uid == null || uid != order.getUser().getId()) {
            return new ResponseEntity("NOT_OWNER", HttpStatus.BAD_REQUEST);
        }
        OrderDTO dto = orderConverter.toDTO(order);
        dto.setPlace(getPlaceLite(order.getPlaceId()));
        return ResponseEntity.ok(dto);
    }

    private PlaceDTOLite getPlaceLite(long placeId) {
        Place place = placeRepository.findById(placeId).get();
        PlaceDTOLite placeDTOLite = placeConverter.toPlaceLite(place);
        String imageName = "Place_" + place.getId() + "_1";
        placeDTOLite.setImageLink(imagePlaceRepository.findByImageName(imageName).getImageLink());
        return placeDTOLite;
    }

    @Override
    public ResponseEntity<?> findByOrderId(long id) {
        Order order = orderRepository.findById(id).get();
        return ResponseEntity.ok(orderConverter.toDTO(order));
    }

    //send ticket
    @Override
    @Transactional
    public ResponseEntity<?> sendTicket(long id) throws DocumentException, IOException, URISyntaxException, MessagingException {
        Optional<Order> orderOptional = orderRepository.findById(id);
        Order order = orderOptional.get();
        if (!order.getRedemptionDate().after(getDateBefore(1))) {
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
            List<Code> codeToUse = codeRepository.findByVisitorTypeIdLimitTo(item.getQuantity(), type, returnToMidnight(order.getRedemptionDate()));
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
            ticketOrder = ticketRepository.saveAll(ticketOrder);
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
        File file = pdfPrinter.printPDF(printRequests, place.getPlaceKey());
        order.setStatus(OrderStatus.SENT.toString());
        orderRepository.save(order);
        sendEmail(order, file);
        return ResponseEntity.ok(orderConverter.toDTO(order));
    }

    //resent ticket
    @Override
    public ResponseEntity<?> resendTicket(long orderId) throws IOException, MessagingException, URISyntaxException, DocumentException {
        Order order = orderRepository.findById(orderId).get();
        if (!order.getRedemptionDate().after(getDateBefore(1))) {
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
            printRequest.setRedemptionDate(order.getRedemptionDate());
            printRequests.add(printRequest);
        }
        File file = pdfPrinter.printPDF(printRequests, place.getPlaceKey());
        order.setStatus(OrderStatus.SENT.toString());
        orderRepository.save(order);
        sendEmail(order, file);
        return ResponseEntity.ok(orderConverter.toDTO(order));
    }

    @Override
    public ResponseEntity<?> getOrderByUid(long id, Long uid) {
        User user = userRepository.findById(id).get();
        if (uid == null || uid != id) {
            return new ResponseEntity("NOT_OWNER", HttpStatus.BAD_REQUEST);
        }
        if (user == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USER_NOT_EXISTED");
        }
        List<OrderDTO> dtoList = new ArrayList<>();
        for (Order order : orderRepository.findAllByUserOrderByCreatedAtDesc(user)) {
            Place place = placeRepository.findById(order.getPlaceId()).get();
            PlaceDTOLite placeDTOLite = placeConverter.toPlaceLite(place);
            for (ImagePlace imagePlace : place.getImagePlace()) {
                placeDTOLite.setImageLink(imagePlace.getImageLink());
                break;
            }
            OrderDTO dto = orderConverter.toDTO(order);
            dto.setPlace(placeDTOLite);
            dtoList.add(dto);
        }
        return ResponseEntity.ok(dtoList);
    }

    @Override
    public ResponseEntity<?> getOrderByUid(long id, Long uid, int limit, int page) {
        User user = userRepository.findById(id).get();
        if (uid == null || uid != id) {
            return new ResponseEntity("NOT_OWNER", HttpStatus.BAD_REQUEST);
        }
        if (user == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USER_NOT_EXISTED");
        }
        List<OrderDTO> dtoList = new ArrayList<>();
        for (Order order : orderRepository.findAllByUserPaging(user.getId(), limit, (page - 1) * limit)) {
            OrderDTO dto = orderConverter.toDTO(order);
            dto.setPlace(getPlaceLite(order.getPlaceId()));
            dtoList.add(dto);
        }
        Output output = new Output();
        output.setListResult(dtoList);
        output.setPage(page);
        int totalItem = orderRepository.countByUser(user);
        output.setTotalItems(totalItem);
        output.setTotalPage((totalItem % limit == 0) ? totalItem / limit : (totalItem / limit) + 1);
        return ResponseEntity.ok(output);
    }

    // send ticket.pdf file to user
    public void sendEmail(Order order, File file) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);

        helper.setTo(order.getMail());
        String orderCode = order.getOrderCode();
        helper.setSubject("Ma don hang: #" + orderCode);

        helper.setText("Hi " + order.getFirstName() + " " + order.getLastName() + ",\n" +
                "Cam on ban da su dung dich vu cua GOBOKI!\n" +
                "De su dung san pham, vui long xuat trinh tai dia diem da lua chon, hoac in ve ra giay.");
        String path1 = file.getPath();

        // Attachment 1
        FileSystemResource file1 = new FileSystemResource(new File(path1));
        helper.addAttachment(orderCode + ".pdf", file1);
        emailSender.send(message);
    }

    @Override
    public ResponseEntity<?> getOrderByUidTop3(long id, Long uid) {
        if (uid == null || uid != id) {
            return new ResponseEntity("NOT_OWNER", HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findById(id).get();
        if (user == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USER_NOT_EXISTED");
        }
        List<OrderDTO> dtoList = new ArrayList<>();
        for (Order order : orderRepository.getTop3(id)) {
            OrderDTO dto = orderConverter.toDTO(order);
            dto.setPlace(getPlaceLite(order.getPlaceId()));
            dtoList.add(dto);
        }
        return ResponseEntity.ok(dtoList);
    }


    static Date getDateBefore(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1 * days);
        cal.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    private Date returnToMidnight(Date redemptionDate) {
        Instant inst = redemptionDate.toInstant();
        LocalDate localDate = inst.atZone(ZoneId.systemDefault()).toLocalDate();
        Instant dayInst = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date day = Date.from(dayInst);
        TimeZone tz = TimeZone.getDefault();
        day = new Date(day.getTime() + tz.getRawOffset());
        return day;
    }


}
