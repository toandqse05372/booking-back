package com.capstone.booking.api;

import com.capstone.booking.entity.dto.TicketDTO;
import com.capstone.booking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;


    //them
    @PostMapping("/ticket")
    public ResponseEntity<?> create(@RequestBody TicketDTO model) {
        return ticketService.create(model);
    }

    //sửa
    @PutMapping("/ticket/{id}")
    public ResponseEntity<?> update(@RequestBody TicketDTO model, @PathVariable("id") long id){
        model.setId(id);
        return ticketService.update(model);
    }

    //xóa
    @DeleteMapping("/ticket/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return ticketService.delete(id);
    }
}
