package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

public interface PlaceTypeCustom {
    Output findByMulParam(String typeName, Long limit, Long page);
}