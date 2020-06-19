package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PaymentMethodsConverter;
import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.PaymentMethods;
import com.capstone.booking.entity.dto.PaymentMethodsDTO;
import com.capstone.booking.repository.OrderRepository;
import com.capstone.booking.repository.PaymentMethodsRepository;
import com.capstone.booking.service.PaymentMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
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
        PaymentMethods methods = methodsConverter.toMethod(methodDTO);

        Order order = orderRepository.findById(methodDTO.getOrderId()).get();
        methods.setOrder(order);

        methodsRepository.save(methods);
        return ResponseEntity.ok(methodsConverter.toDTO(methods));
    }


    //sửa
    @Override
    public ResponseEntity<?> update(PaymentMethodsDTO methodDTO) {
        PaymentMethods methods = new PaymentMethods();
        PaymentMethods oldMethod = methodsRepository.findById(methodDTO.getId()).get();
        methods = methodsConverter.toMethod(methodDTO, oldMethod);

        Order order = orderRepository.findById(methodDTO.getOrderId()).get();
        methods.setOrder(order);

        methodsRepository.save(methods);
        return ResponseEntity.ok(methodsConverter.toDTO(methods));
    }


    @Override
    public void delete(long id) {
        methodsRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<?> findByMulParam(String methodName, Long limit, Long page) {
        Output results = methodsRepository.findByMulParam(methodName, limit, page);
        return ResponseEntity.ok(results);
    }
}
