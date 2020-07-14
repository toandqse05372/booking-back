package com.capstone.booking.service;

import com.capstone.booking.entity.dto.VisitorTypeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface VisitorTypeService {
    //add
    ResponseEntity<?> create(VisitorTypeDTO model);

    //edit
    ResponseEntity<?> update(VisitorTypeDTO model);

    //delete
    ResponseEntity<?> delete(long id);

    //search by ticketTypeId
    ResponseEntity<?> findByTicketTypeId(long id);

    //search by Id
    ResponseEntity<?> getById(long id);

    ResponseEntity<?> addCodeForTicketType(MultipartFile file, String codeType);
}
