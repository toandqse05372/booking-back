package com.capstone.booking.service;

import com.capstone.booking.entity.dto.PaymentMethodsDTO;
import org.springframework.http.ResponseEntity;

public interface PaymentMethodsService {
    //add
    ResponseEntity<?> create(PaymentMethodsDTO methodDTO);

    //edit
    ResponseEntity<?> update(PaymentMethodsDTO methodDTO);

    //delete
    ResponseEntity<?> delete(long id);

    //search PaymentMethods by name & paging
    ResponseEntity<?> findByName(String methodName, Long limit, Long page);

    //search by Id
    ResponseEntity<?> getMethod(Long id);
}
