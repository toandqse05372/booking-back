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
        orderService.delete(id);
        return new ResponseEntity("Delete Successful", HttpStatus.OK);
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
