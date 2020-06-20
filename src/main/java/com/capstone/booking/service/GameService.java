package com.capstone.booking.service;
import com.capstone.booking.entity.dto.GameDTO;
import org.springframework.http.ResponseEntity;


public interface GameService {

    //Thêm
    ResponseEntity<?> create(GameDTO gameDTO);

    //sửa
    ResponseEntity<?> update(GameDTO gameDTO);

    //delete game
    void delete(long id);

    //search by Id
    ResponseEntity<?> getGame(Long id);

    //GetAllGame
    ResponseEntity<?> findAll();

    //tim kiem Game theo name & placeName, & paging ============================
    ResponseEntity<?> findByMulParam(String gameName, String placeName, Long limit, Long page);

    //change status
    ResponseEntity<?> changeStatus(Long id);

    //tim kiem Game theo placeId & paging
    ResponseEntity<?> findByPlaceId(Long placeId, Long limit, Long page);

}
