package com.capstone.booking.api;

import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.service.OrderService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    //delete
    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteMethod(@PathVariable("id") long id) {
        return orderService.delete(id);
    }

    //search Order by status, & paging
    @GetMapping("/order/searchByStatus")
    public ResponseEntity<?> searchByStatus(@RequestParam(value = "status", required = false) String status,
                                            @RequestParam(value = "code", required = false) String code) {
        return orderService.findByStatus(status, code);
    }

    //add
    @PostMapping("/order")
    public ResponseEntity<?> createMethod(@RequestBody OrderDTO model) {
        return orderService.create(model);
    }

    //edit
    @PutMapping("/order/{id}")
    public ResponseEntity<?> updateMethod(@RequestBody OrderDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return orderService.update(model);
    }

    @PutMapping("/order/sendTicket/{id}")
    public ResponseEntity<?> sendTicket(@PathVariable("id") long id) throws DocumentException, IOException, URISyntaxException {
        return orderService.sendTicket(id);
    }

    //search by Id
    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") long id) {
        return orderService.findByOrderId(id);
    }
}
