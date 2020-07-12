package com.capstone.booking.service;

import com.capstone.booking.entity.dto.VisitorTypeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface VisitorTypeService {
    //them
    ResponseEntity<?> create(VisitorTypeDTO model);

    //sửa
    ResponseEntity<?> update(VisitorTypeDTO model);

    //xoa
    ResponseEntity<?> delete(long id);

    ResponseEntity<?> findByTicketTypeId(long id);

    ResponseEntity<?> getById(long id);

    ResponseEntity<?> addCodeForTicketType(MultipartFile file, String codeType);
}
