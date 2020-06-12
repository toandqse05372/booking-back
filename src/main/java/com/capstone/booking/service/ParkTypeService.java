package com.capstone.booking.service;

import com.capstone.booking.entity.dto.ParkTypeDTO;
import org.springframework.http.ResponseEntity;

public interface ParkTypeService {
    //find all
    ResponseEntity<?> getAllParkType();

    //xóa
    void delete(Long id);

    //them
    ResponseEntity<?> create(ParkTypeDTO parkTypeDTO);

    //sưa
    ResponseEntity<?> update(ParkTypeDTO parkTypeDTO);

}
