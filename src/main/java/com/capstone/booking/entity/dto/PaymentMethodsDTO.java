package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PaymentMethodsDTO extends BaseDTO{
    private String methodName;
    private String methodKey;
    private String status;
}
