package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PaymentMethodsConverter;
import com.capstone.booking.common.key.PlaceAndGameStatus;
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
        methodsRepository.save(method);
        return ResponseEntity.ok(methodsConverter.toDTO(method));
    }


    @Override
    public ResponseEntity<?> delete(long id) {
        if (!methodsRepository.findById(id).isPresent()) {
            return new ResponseEntity("Id already exists", HttpStatus.BAD_REQUEST);
        }
        methodsRepository.deleteById(id);
        return new ResponseEntity("Delete Successful", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByMulParam(String methodName, Long limit, Long page) {
        Output results = methodsRepository.findByMulParam(methodName, limit, page);
        return ResponseEntity.ok(results);
    }
}
