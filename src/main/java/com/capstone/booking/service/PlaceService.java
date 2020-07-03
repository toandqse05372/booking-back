package com.capstone.booking.service;
import com.capstone.booking.entity.dto.PlaceDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PlaceService {

    //tim kiem Place theo ten & address, description, cityId, categoryId, & paging
    ResponseEntity<?> findByMultipleParam(String name, String address, Long cityId,
                                          Long categoryId, Long limit, Long page);

    //search ById
    ResponseEntity<?> getPlace(Long id);

    //them place
    ResponseEntity<?> create(PlaceDTO placeDTO, MultipartFile[] files) ;

    //sưa place
    ResponseEntity<?> update(PlaceDTO placeDTO, MultipartFile[] files) ;

    //xóa
    ResponseEntity<?> delete(long id);

    //change status
    ResponseEntity<?> changeStatus(Long id);

    ResponseEntity<?> getAll();

}
