package com.capstone.booking.service;

import com.capstone.booking.entity.dto.TicketDTO;
import org.springframework.http.ResponseEntity;

public interface TicketService {

    //Thêm
    ResponseEntity<?> create(TicketDTO ticketDTO);

    //sửa
    ResponseEntity<?> update(TicketDTO ticketDTO);

    //delete
    ResponseEntity<?> delete(long id);
}
