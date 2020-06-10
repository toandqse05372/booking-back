package com.capstone.booking.service;
import com.capstone.booking.entity.dto.ParkDTO;
import org.springframework.http.ResponseEntity;

public interface ParkService {

    //tim kiem Park theo ten & address, description, cityId, parkTypeId, & paging
    ResponseEntity<?> findByMultipleParam(String name, String address, Long cityId,
                                          Long parkTypeId, Long limit, Long page);

    //search ById
    ResponseEntity<?> getPark(Long id);

    //them
    ResponseEntity<?> create(ParkDTO parkDTO);

    //sửa
    ResponseEntity<?> update(ParkDTO parkDTO);
    //xóa
    void delete(long id);

    ResponseEntity<?> getAllParkType();
}
