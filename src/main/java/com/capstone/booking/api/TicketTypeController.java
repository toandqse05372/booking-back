package com.capstone.booking.api;

import com.capstone.booking.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketTypeController {
    @Autowired
    private TicketTypeService ticketTypeService;

    //find all
    @GetMapping("/ticketType")
    public ResponseEntity<?> searchAll() {
        return ticketTypeService.findAll();
    }


}
