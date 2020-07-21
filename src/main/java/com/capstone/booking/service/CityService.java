package com.capstone.booking.service;

import com.capstone.booking.entity.dto.CityDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CityService {
    //getAllCity
    ResponseEntity<?> findAllCity();

    //getTop3
    ResponseEntity<?> getTop3();

    //search By Id
    ResponseEntity<?> getCity(Long id);

    //search cityName & paging
    ResponseEntity<?> findByName(String name, Long limit, Long page);

    //add
    ResponseEntity<?> create(CityDTO cityDTO, MultipartFile file);

    //edit
    ResponseEntity<?> update(CityDTO cityDTO, MultipartFile file);

    //delete city
    ResponseEntity<?> delete(long id);
}
