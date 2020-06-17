package com.capstone.booking.api;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    //find all
    @GetMapping("/game")
    public ResponseEntity<?> searchAll() {
        return gameService.findAll();
    }

    //tim kiem Game theo name & placeName, & paging
    @GetMapping("/game/searchMul")
    public ResponseEntity<?> searchMUL(@RequestParam(value = "gameName", required = false) String gameName,
                                       @RequestParam(value = "limit", required = false) Long limit,
                                       @RequestParam(value = "page", required = false) Long page,
                                       @RequestParam(value = "placeName", required = false) String placeName) {
        return gameService.findByMulParam(gameName, placeName, limit, page);
    }

    //delete Game
    @DeleteMapping("/game/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable("id") long id) {
        gameService.delete(id);
        return new ResponseEntity("Delete Successful", HttpStatus.OK);
    }

    //change status Game
    @PutMapping("/changeGame/{id}")
    public ResponseEntity<?> changeStatusGame(@PathVariable("id") long id) {
        gameService.changeStatus(id);
        return new ResponseEntity("Change Successful", HttpStatus.OK);
    }

    //add Game
    @PostMapping("/game")
    public ResponseEntity<?> createGame(@RequestBody GameDTO model) {
        return gameService.create(model);
    }
    //edit Game
    @PutMapping("/game/{id}")
    public ResponseEntity<?> updateGame(@RequestBody GameDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return gameService.update(model);
    }

    //search by Id
    @GetMapping("/game/{id}")
    public ResponseEntity<?> getGame(@PathVariable Long id) {
        return gameService.getGame(id);
    }

}
