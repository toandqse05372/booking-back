package com.capstone.booking.service;

import com.capstone.booking.entity.dto.TicketDTO;
import org.springframework.http.ResponseEntity;

public interface TicketService {

    //edd
    ResponseEntity<?> create(TicketDTO ticketDTO);

    //edit
    ResponseEntity<?> update(TicketDTO ticketDTO);

    //delete
    ResponseEntity<?> delete(long id);

    ResponseEntity<?> getReport(Long placeId, Long reportType, Long startDate, Long endDate);
}
