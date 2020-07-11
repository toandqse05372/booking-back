package com.capstone.booking.service;
import com.capstone.booking.entity.dto.CityDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface CityService {

    ResponseEntity<?> findAllCity();
    //search ById
    ResponseEntity<?> getCity(Long id);

    //search cityName & paging
    ResponseEntity<?> findByName(String name, Long limit, Long page);

    //Thêm
    ResponseEntity<?> create(CityDTO cityDTO, MultipartFile file);

    //sửa
    ResponseEntity<?> update(CityDTO cityDTO, MultipartFile file);

    //delete city
    ResponseEntity<?> delete(long id);
}
