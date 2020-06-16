package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

public interface TicketTypeCustom {
    Output findByTypeName(String typeName, Long limit, Long page);
}