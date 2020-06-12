package com.capstone.booking.service;

import org.springframework.http.ResponseEntity;

public interface TicketTypeService {
    ResponseEntity<?> findAll();
}
