package com.capstone.booking.service;
import com.capstone.booking.entity.dto.PlaceDTO;
import org.springframework.http.ResponseEntity;

public interface PlaceService {

    //tim kiem Place theo ten & address, description, cityId, placeTypeId, & paging
    ResponseEntity<?> findByMultipleParam(String name, String address, Long cityId,
                                          Long placeTypeId, Long limit, Long page);

    //search ById
    ResponseEntity<?> getPlace(Long id);

    //them
    ResponseEntity<?> create(PlaceDTO placeDTO);

    //sửa
    ResponseEntity<?> update(PlaceDTO placeDTO);
    //xóa
    void delete(long id);

}