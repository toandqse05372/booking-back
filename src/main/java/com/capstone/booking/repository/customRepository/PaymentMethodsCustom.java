package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

public interface PaymentMethodsCustom {
    Output findByMulParam(String methodName, Long limit, Long page);
}
