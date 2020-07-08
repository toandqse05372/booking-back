package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PaymentMethodsConverter;
import com.capstone.booking.common.key.PlaceAndGameStatus;
import com.capstone.booking.entity.City;
import com.capstone.booking.entity.PaymentMethods;
import com.capstone.booking.entity.dto.PaymentMethodsDTO;
import com.capstone.booking.repository.OrderRepository;
import com.capstone.booking.repository.PaymentMethodsRepository;
import com.capstone.booking.service.PaymentMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodsServiceIml implements PaymentMethodsService {

    @Autowired
    private PaymentMethodsRepository methodsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentMethodsConverter methodsConverter;

    //thêm
    @Override
    public ResponseEntity<?> create(PaymentMethodsDTO methodDTO) {
        PaymentMethods method = methodsConverter.toMethod(methodDTO);
        if (methodsRepository.findByMethodName(method.getMethodName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PAYMENT_METHOD_EXISTED");
        }
        method.setStatus(PlaceAndGameStatus.ACTIVE.toString());
        methodsRepository.save(method);
        return ResponseEntity.ok(methodsConverter.toDTO(method));
    }


    //sửa
    @Override
    public ResponseEntity<?> update(PaymentMethodsDTO methodDTO) {
        PaymentMethods method = new PaymentMethods();
        PaymentMethods oldMethod = methodsRepository.findById(methodDTO.getId()).get();
        method = methodsConverter.toMethod(methodDTO, oldMethod);
        PaymentMethods existedMethod = methodsRepository.findByMethodName(method.getMethodName());
        if (existedMethod != null && existedMethod.getId() != method.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PAYMENT_METHOD_EXISTED");
        }
        methodsRepository.save(method);
        return ResponseEntity.ok(methodsConverter.toDTO(method));
    }


    @Override
    public ResponseEntity<?> delete(long id) {
        if (!methodsRepository.findById(id).isPresent()) {
            return new ResponseEntity("PAYMENT_METHOD_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        methodsRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByName(String methodName, Long limit, Long page) {
        Output results = methodsRepository.findByMulParam(methodName, limit, page);
        return ResponseEntity.ok(results);
    }

    @Override
    public ResponseEntity<?> getMethod(Long id) {
        PaymentMethodsDTO dto = methodsConverter.toDTO(methodsRepository.findById(id).get());
        return ResponseEntity.ok(dto);
    }
}
