package com.capstone.booking.api;

import com.capstone.booking.entity.dto.PaymentMethodsDTO;
import com.capstone.booking.service.PaymentMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentMethodsController {

    @Autowired
    private PaymentMethodsService methodService;

    //search PaymentMethods by name & paging
    @GetMapping("/method/searchByName")
    @PreAuthorize("hasAnyAuthority('PAYMENT_METHOD_EDIT')")
    public ResponseEntity<?> searchMUL(@RequestParam(value = "methodName", required = false) String methodName,
                                       @RequestParam(value = "limit", required = false) Long limit,
                                       @RequestParam(value = "page", required = false) Long page) {
        return methodService.findByName(methodName, limit, page);
    }

    //delete
    @DeleteMapping("/method/{id}")
    @PreAuthorize("hasAnyAuthority('PAYMENT_METHOD_EDIT')")
    public ResponseEntity<?> deleteMethod(@PathVariable("id") long id) {
        return methodService.delete(id);
    }

    //add
    @PostMapping("/method")
    @PreAuthorize("hasAnyAuthority('PAYMENT_METHOD_EDIT')")
    public ResponseEntity<?> createMethod(@RequestBody PaymentMethodsDTO model) {
        return methodService.create(model);
    }

    //edit
    @PutMapping("/method/{id}")
    @PreAuthorize("hasAnyAuthority('PAYMENT_METHOD_EDIT')")
    public ResponseEntity<?> updateMethod(@RequestBody PaymentMethodsDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return methodService.update(model);
    }

    //search by Id
    @GetMapping("/method/{id}")
    public ResponseEntity<?> getMethod(@PathVariable Long id) {
        return methodService.getMethod(id);
    }
}
