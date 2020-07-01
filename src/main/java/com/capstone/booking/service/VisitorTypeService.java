package com.capstone.booking.service;

import com.capstone.booking.entity.dto.VisitorTypeDTO;
import org.springframework.http.ResponseEntity;

public interface VisitorTypeService {
    //them
    ResponseEntity<?> create(VisitorTypeDTO model);

    //sửa
    ResponseEntity<?> update(VisitorTypeDTO model);

    //xoa
    ResponseEntity<?> delete(long id);
}
