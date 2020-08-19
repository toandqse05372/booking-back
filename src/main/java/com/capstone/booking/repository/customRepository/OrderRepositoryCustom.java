package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

//customer query to order table
public interface OrderRepositoryCustom {
    // find by code and status
    Output findByStatus(String status, String code, Long placeId, Long page, Long limit);
}
