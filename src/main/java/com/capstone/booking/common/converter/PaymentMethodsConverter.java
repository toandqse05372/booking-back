package com.capstone.booking.common.converter;

import com.capstone.booking.entity.PaymentMethods;
import com.capstone.booking.entity.dto.PaymentMethodsDTO;
import org.springframework.stereotype.Component;


@Component
public class PaymentMethodsConverter {
    public PaymentMethods toMethod(PaymentMethodsDTO dto) {
        PaymentMethods methods = new PaymentMethods();
        methods.setMethodKey(dto.getMethodKey());
        methods.setStatus(dto.getStatus());
        return methods;
    }

    public PaymentMethods toMethod(PaymentMethodsDTO dto, PaymentMethods methods) {
        methods.setMethodKey(dto.getMethodKey());
        methods.setStatus(dto.getStatus());
        return methods;
    }

    public PaymentMethodsDTO toDTO(PaymentMethods methods) {
        PaymentMethodsDTO dto = new PaymentMethodsDTO();
        if (methods.getId() != null) {
            dto.setId(methods.getId());
        }
        dto.setMethodKey(methods.getMethodKey());
        dto.setStatus(methods.getStatus());

        dto.setPaymentId(methods.getPayment().getId());
        return dto;
    }
}
