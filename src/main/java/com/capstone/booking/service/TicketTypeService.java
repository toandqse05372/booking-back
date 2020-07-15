package com.capstone.booking.service;

import com.capstone.booking.entity.dto.TicketTypeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface TicketTypeService {
    //get All
    ResponseEntity<?> findAll();

    //delete
    ResponseEntity<?> delete(long id);

    //add
    ResponseEntity<?> create(TicketTypeDTO ticketTypeDTO);

    //edit
    ResponseEntity<?> update(TicketTypeDTO ticketTypeDTO);

    //search by placeId
    ResponseEntity<?> findByPlaceId(long placeId);

    //search ticketType by typeName & paging
    ResponseEntity<?> findByTypeName(String typeName, Long limit, Long page);

    //search by Id
    ResponseEntity<?> getTicketType(Long id);

    ResponseEntity<?> addCodeForTicketType(MultipartFile file);
}
