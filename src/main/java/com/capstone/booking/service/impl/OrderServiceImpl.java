package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.OrderConverter;
import com.capstone.booking.common.converter.OrderItemConverter;
import com.capstone.booking.common.helper.PdfPrinter;
import com.capstone.booking.common.helper.PrintRequest;
import com.capstone.booking.common.key.OrderStatus;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.entity.dto.OrderItemDTO;
import com.capstone.booking.repository.*;
import com.capstone.booking.service.OrderService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private PdfPrinter pdfPrinter;

    @Override
    public ResponseEntity<?> create(OrderDTO orderDTO) {
        Order order = orderConverter.toOrder(orderDTO);

        User user = userRepository.findById(orderDTO.getUserId()).get();
        order.setUser(user);

        order.setOrderCode("ORDER"+(orderRepository.findTopByOrderById().getId()+1));
        order.setStatus(OrderStatus.UNPAID.toString());

        for(OrderItemDTO dto: orderDTO.getOrderItems()){
            OrderItem orderItem = orderItemConverter.toItem(dto);
        }
        return ResponseEntity.ok(orderConverter.toDTO(order));
    }

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

    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        if (!orderRepository.findById(id).isPresent()) {
            return new ResponseEntity("ORDER_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        orderRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

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

    @Override
    public ResponseEntity<?> sendTicket(long id) throws DocumentException, IOException, URISyntaxException {
        Order order = orderRepository.findById(id).get();
        Set<OrderItem> orderItems = order.getOrderItem();
        List<PrintRequest> printRequests = new ArrayList<>();
        for(OrderItem item: orderItems){
            VisitorType type = item.getVisitorType();
            List<Code> codeToUse = codeRepository.findByVisitorTypeIdLimitTo(item.getQuantity(), type);
            List<Ticket> ticketOrder = new ArrayList<>();
            for(int i = 0; i < item.getQuantity(); i++){
                Ticket ticket = new Ticket();
                ticket.setCode(codeToUse.get(i).getCode());
     //           ticket.setRedemptionDate(order.getRedemptionDate());
                ticket.setOrderItem(item);
     //           ticket.setVisitorTypeId(type.getId());
                ticketOrder.add(ticket);
            }
            ticketRepository.saveAll(ticketOrder);
            codeRepository.deleteAll(codeToUse);
            PrintRequest printRequest = new PrintRequest();
            printRequest.setTickets(ticketOrder);
            printRequest.setVisitorType(type);
            printRequest.setTicketType(ticketTypeRepository.findById(order.getTicketTypeId()).get());
            printRequests.add(printRequest);
        }
        pdfPrinter.printPDF(printRequests);
        return null;
    }
}
