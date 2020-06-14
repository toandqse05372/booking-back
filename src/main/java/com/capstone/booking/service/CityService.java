package com.capstone.booking.service;
import com.capstone.booking.entity.dto.CityDTO;
import com.capstone.booking.entity.dto.GameDTO;
import org.springframework.http.ResponseEntity;


public interface CityService {

    ResponseEntity<?> findAllCity();
    //search ById
    ResponseEntity<?> getCity(Long id);

    //search cityName & paging
    ResponseEntity<?> findByName(String name, Long limit, Long page);

    //Thêm
    ResponseEntity<?> create(CityDTO cityDTO);

    //sửa
    ResponseEntity<?> update(CityDTO cityDTO);

    //delete city
    void delete(long id);
}
