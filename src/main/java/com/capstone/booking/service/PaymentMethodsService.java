package com.capstone.booking.service;

import com.capstone.booking.entity.dto.PaymentMethodsDTO;
import org.springframework.http.ResponseEntity;

public interface PaymentMethodsService {
    //Thêm
    ResponseEntity<?> create(PaymentMethodsDTO methodDTO);

    //sửa
    ResponseEntity<?> update(PaymentMethodsDTO methodDTO);

    //delete
    void delete(long id);
}