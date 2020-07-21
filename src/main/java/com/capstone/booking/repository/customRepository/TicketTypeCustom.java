package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.entity.TicketType;

import java.util.List;

//custom query ticket type table
public interface TicketTypeCustom {
    List<TicketType> findByPlaceId(Long placeId);

    List<TicketType> findByPlaceIdAndStatus(Long placeId, String status);
}
