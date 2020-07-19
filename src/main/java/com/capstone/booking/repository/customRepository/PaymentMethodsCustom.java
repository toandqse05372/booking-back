package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

//note use
public interface PaymentMethodsCustom {
    Output findByMulParam(String methodName, Long limit, Long page);
}
