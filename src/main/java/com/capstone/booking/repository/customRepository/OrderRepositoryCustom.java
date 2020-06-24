package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

public interface OrderRepositoryCustom {
    Output findByStatus(String status, Long limit, Long page);
}
