package com.capstone.booking.api;

import com.capstone.booking.entity.dto.TicketDTO;
import com.capstone.booking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;


    //add
    @PostMapping("/ticket")
    public ResponseEntity<?> create(@RequestBody TicketDTO model) {
        return ticketService.create(model);
    }

    //edit
    @PutMapping("/ticket/{id}")
    public ResponseEntity<?> update(@RequestBody TicketDTO model, @PathVariable("id") long id){
        model.setId(id);
        return ticketService.update(model);
    }

    //delete
    @DeleteMapping("/ticket/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return ticketService.delete(id);
    }

    @GetMapping("/report")
    public ResponseEntity<?> searchForReport(@RequestParam(value = "placeId") Long placeId,
                                             @RequestParam(value = "type") Long reportType,
                                             @RequestParam(value = "startDate", required = false) Long startDate,
                                             @RequestParam(value = "endDate", required = false) Long endDate){
        return ticketService.getReport(placeId, reportType, startDate, endDate);
    }
}
