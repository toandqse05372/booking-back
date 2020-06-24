package com.capstone.booking.api;

import com.capstone.booking.entity.dto.PaymentMethodsDTO;
import com.capstone.booking.service.PaymentMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentMethodsController {

    @Autowired
    private PaymentMethodsService methodService;

    //tim kiem PaymentMethods theo name & paging
    @GetMapping("/method/searchByName")
    public ResponseEntity<?> searchMUL(@RequestParam(value = "methodName", required = false) String methodName,
                                       @RequestParam(value = "limit", required = false) Long limit,
                                       @RequestParam(value = "page", required = false) Long page) {
        return methodService.findByMulParam(methodName, limit, page);
    }

    //delete
    @DeleteMapping("/method/{id}")
    public ResponseEntity<?> deleteMethod(@PathVariable("id") long id) {
        return methodService.delete(id);
    }

    //add
    @PostMapping("/method")
    public ResponseEntity<?> createMethod(@RequestBody PaymentMethodsDTO model) {
        return methodService.create(model);
    }

    //edit
    @PutMapping("/method/{id}")
    public ResponseEntity<?> updateMethod(@RequestBody PaymentMethodsDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return methodService.update(model);
    }
}
