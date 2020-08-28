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
                                         @RequestParam(value = "placeId", required = false) Long placeId,
                                         @RequestParam(value = "page", required = false) Long page,
                                         @RequestParam(value = "limit", required = false) Long limit) {
        return orderService.findByStatus(status, code, placeId, page, limit);
    }

    //add api
    @PostMapping("/order")
    @PreAuthorize("hasAnyAuthority('OWN_ORDER_READ')")
    public ResponseEntity<?> create(@RequestBody OrderDTO model) {
        return orderService.create(model, OrderStatus.UNPAID, null);
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
    @PostMapping("/order/{id}")
    @PreAuthorize("hasAnyAuthority('OWN_ORDER_READ')")
    public ResponseEntity<?> getOrderById(@PathVariable("id") long id, @RequestPart(value = "uid") String uid) {
        return orderService.findByOrderId(id, Long.parseLong(uid));
    }

    //search by Id api
    @GetMapping("/orderCMS/{id}")
    @PreAuthorize("hasAnyAuthority('ORDER_EDIT')")
    public ResponseEntity<?> getOrderByIdCMS(@PathVariable("id") long id) {
        return orderService.findByOrderId(id);
    }

    @PostMapping("/order/user/{id}")
    @PreAuthorize("hasAnyAuthority('OWN_ORDER_READ')")
    public ResponseEntity<?> getOrdersByUid(@PathVariable("id") long id,
                                            @RequestPart(value = "uid") String uid,
                                            @RequestPart(value = "page") String page,
                                            @RequestPart(value = "limit") String limit){
        return orderService.getOrderByUid(id, Long.parseLong(uid), Integer.parseInt(limit), Integer.parseInt(page));
    }

    @PostMapping("/order/top3/{id}")
    @PreAuthorize("hasAnyAuthority('OWN_ORDER_READ')")
    public ResponseEntity<?> getOrdersByUidTop3(@PathVariable("id") long id, @RequestPart(value = "uid") String uid){
        return orderService.getOrderByUidTop3(id, Long.parseLong(uid));
    }
}
