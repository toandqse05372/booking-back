package com.capstone.booking.api;

import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    //delete
    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteMethod(@PathVariable("id") long id) {
        return orderService.delete(id);
    }

    //tim kiem Order theo status, & paging
    @GetMapping("/order/searchByStatus")
    public ResponseEntity<?> searchByStatus(@RequestParam(value = "status", required = false) String status,
                                       @RequestParam(value = "limit", required = false) Long limit,
                                       @RequestParam(value = "page", required = false) Long page) {
        return orderService.findByStatus(status, limit, page);
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
}
