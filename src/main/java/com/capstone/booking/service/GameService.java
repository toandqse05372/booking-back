package com.capstone.booking.service;

import com.capstone.booking.entity.dto.GameDTO;
import org.springframework.http.ResponseEntity;

public interface GameService {

    //add
    ResponseEntity<?> create(GameDTO gameDTO);

    //edit
    ResponseEntity<?> update(GameDTO gameDTO);

    //delete game
    ResponseEntity<?> delete(long id);

    //search by Id
    ResponseEntity<?> getGame(Long id);

    //GetAllGame
    ResponseEntity<?> findAll();

    //search Game by name & placeName, & paging
    ResponseEntity<?> findByMulParam(String gameName, String placeName, Long limit, Long page);

    //change status
    ResponseEntity<?> changeStatus(Long id);

    //search Game by placeId & paging
    ResponseEntity<?> findByPlaceId(Long placeId, Long limit, Long page);

    ResponseEntity<?> listOptionByPlace(long id);
}
