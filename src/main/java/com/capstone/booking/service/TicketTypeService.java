package com.capstone.booking.service;

import com.capstone.booking.entity.dto.TicketTypeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public interface TicketTypeService {
    //get All
    ResponseEntity<?> findAll();

    //delete
    ResponseEntity<?> delete(long id);

    //add
    ResponseEntity<?> create(TicketTypeDTO ticketTypeDTO);

    //edit
    ResponseEntity<?> update(TicketTypeDTO ticketTypeDTO);

    //change status of ticket type
    ResponseEntity<?> changeStatus(Long id);

    //search by placeId
    ResponseEntity<?> findByPlaceId(long placeId, Date date);

    //search by Id
    ResponseEntity<?> getTicketType(Long id);

    ResponseEntity<?> addCodeFromExcel(MultipartFile file, long l, Date date);
}
