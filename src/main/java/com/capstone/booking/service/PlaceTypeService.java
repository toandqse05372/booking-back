package com.capstone.booking.service;

import com.capstone.booking.entity.dto.PlaceTypeDTO;
import org.springframework.http.ResponseEntity;

public interface PlaceTypeService {
    //find all
    ResponseEntity<?> getAllPlaceType();

    //xóa
    void delete(Long id);

    //them
    ResponseEntity<?> create(PlaceTypeDTO placeTypeDTO);

    //sưa
    ResponseEntity<?> update(PlaceTypeDTO placeTypeDTO);

}
