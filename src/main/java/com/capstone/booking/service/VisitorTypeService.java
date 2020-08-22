package com.capstone.booking.service;

import com.capstone.booking.entity.dto.VisitorTypeDTO;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface VisitorTypeService {
    //add
    ResponseEntity<?> create(VisitorTypeDTO model, Long placeId);

    //edit
    ResponseEntity<?> update(VisitorTypeDTO model, Long placeId);

    //delete
    ResponseEntity<?> delete(long id);

    //change status of visitor type
    ResponseEntity<?> changeStatus(long id);

    //search by ticketTypeId
    ResponseEntity<?> findByTicketTypeId(long id);

    //search by Id
    ResponseEntity<?> getById(long id);

    //not used
//    ResponseEntity<?> addCodeForVisitorType(MultipartFile file, String codeType);

    //set visitor type's price as basic place
    ResponseEntity<?> markBasicPrice(long id, long placeId);

    ResponseEntity<?> findByTicketTypeIdAndDate(Long ticketTypeId, Date convertDate);

    ResponseEntity<?> getVisitorTypeRemaining(String visitorTypeIds, Date date);
}
