package com.capstone.booking.service;

import com.capstone.booking.entity.dto.PlaceDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface PlaceService {

    //search place by name & address, cityId, categoryId, & paging
    ResponseEntity<?> findByMultipleParam(String name, String address, Long cityId,
                                          Long categoryId, Long limit, Long page);

    //search ById
    ResponseEntity<?> getPlace(Long id);

    //add place
    ResponseEntity<?> create(PlaceDTO placeDTO, MultipartFile[] files) ;

    //edit place
    ResponseEntity<?> update(PlaceDTO placeDTO, MultipartFile[] files) ;

    //delete
    ResponseEntity<?> delete(long id);

    //change status
    ResponseEntity<?> changeStatus(Long id);

    //getAll place
    ResponseEntity<?> getAll();

    ResponseEntity<?> searchPlaceForClient(String name, Long minValue, Long maxValue, List<Long> cityId,
                                           List<Long> categoryId, Long limit, Long page);

    ResponseEntity<?> getPlaceClient(Long id);

    //getTop8Place
    ResponseEntity<?> getTop8PlaceByCityId();

    //getPlaceFromHN
    ResponseEntity<?> getPlaceFromHN();

    //getPlaceFromHN
    ResponseEntity<?> getPlaceFromDN();

    //getPlaceFromHN
    ResponseEntity<?> getPlaceFromHCM();
}
