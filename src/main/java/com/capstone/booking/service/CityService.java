package com.capstone.booking.service;
import org.springframework.http.ResponseEntity;


public interface CityService {

    ResponseEntity<?> findAllCity();
    //search ById
    ResponseEntity<?> getCity(Long id);

    //search cityName & paging
    ResponseEntity<?> findByName(String name, Long limit, Long page);
}
