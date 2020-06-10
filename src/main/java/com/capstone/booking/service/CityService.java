package com.capstone.booking.service;
import org.springframework.http.ResponseEntity;


public interface CityService {

    ResponseEntity<?> findAllCity();
    //search ById
    ResponseEntity<?> getCity(Long id);
}
