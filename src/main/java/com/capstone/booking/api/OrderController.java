package com.capstone.booking.api;

import com.capstone.booking.common.key.OrderStatus;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.common.helper.pdf.PrintTicketRequest;
import com.capstone.booking.service.OrderService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URISyntaxException;

//order's api
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    //delete api
    @DeleteMapping("/order/{id}")
    @PreAuthorize("hasAnyAuthority('ORDER_READ')")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return orderService.delete(id);
    }

    //search Order by status, code api
    @GetMapping("/order/searchByStatus")
    @PreAuthorize("hasAnyAuthority('ORDER_READ')")
    public ResponseEntity<?> orderFilter(@RequestParam(value = "status", required = false) String status,
                                         @RequestParam(value = "code", required = false) String code,
                                         @RequestParam(value = "placeId", required = false) Long placeId) {
        return orderService.findByStatus(status, code, placeId);
    }

    //add api
    @PostMapping("/order")
    @PreAuthorize("hasAnyAuthority('OWN_ORDER_READ')")
    public ResponseEntity<?> create(@RequestBody OrderDTO model) {
        return orderService.create(model, OrderStatus.UNPAID);
    }

    //send ticket to customer api
    @PostMapping("/order/sendTicket")
    @PreAuthorize("hasAnyAuthority('ORDER_READ')")
    public ResponseEntity<?> sendTicket(@RequestBody PrintTicketRequest request) throws DocumentException, IOException, URISyntaxException, MessagingException {
        if (request.getType() == 2) {
            return  orderService.resendTicket(request.getOrderId());
        } else
            return orderService.sendTicket(request.getOrderId());
    }

    //search by Id api
    @GetMapping("/order/{id}")
    @PreAuthorize("hasAnyAuthority('OWN_ORDER_READ')")
    public ResponseEntity<?> getOrderById(@PathVariable("id") long id) {
        return orderService.findByOrderId(id);
    }

    @GetMapping("/order/user/{id}")
    @PreAuthorize("hasAnyAuthority('OWN_ORDER_READ')")
    public ResponseEntity<?> getOrdersByUid(@PathVariable("id") long id){
        return orderService.getOrderByUid(id);
    }

    @GetMapping("/order/top3/{id}")
    @PreAuthorize("hasAnyAuthority('OWN_ORDER_READ')")
    public ResponseEntity<?> getOrdersByUidTop3(@PathVariable("id") long id){
        return orderService.getOrderByUidTop3(id);
    }
}
