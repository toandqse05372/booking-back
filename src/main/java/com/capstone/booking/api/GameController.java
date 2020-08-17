package com.capstone.booking.api;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//Game's api
@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    //find all
    @GetMapping("/game")
    public ResponseEntity<?> searchAll() {
        return gameService.findAll();
    }

    //search Game by name & placeName, & paging
    @GetMapping("/game/searchMul")
    @PreAuthorize("hasAnyAuthority('GAME_EDIT')")
    public ResponseEntity<?> searchMUL(@RequestParam(value = "gameName", required = false) String gameName,
                                       @RequestParam(value = "limit", required = false) Long limit,
                                       @RequestParam(value = "page", required = false) Long page,
                                       @RequestParam(value = "placeName", required = false) String placeName) {
        return gameService.findByMulParam(gameName, placeName, limit, page);
    }

    //delete Game api
    @DeleteMapping("/game/{id}")
    @PreAuthorize("hasAnyAuthority('GAME_EDIT')")
    public ResponseEntity<?> deleteGame(@PathVariable("id") long id) {
        return gameService.delete(id);
    }

    //change status Game api
    @PutMapping("/changeGame/{id}")
    @PreAuthorize("hasAnyAuthority('GAME_EDIT')")
    public ResponseEntity<?> changeStatusGame(@PathVariable("id") long id) {
        gameService.changeStatus(id);
        return new ResponseEntity("Change Successful", HttpStatus.OK);
    }

    //add Game api
    @PostMapping("/game")
    @PreAuthorize("hasAnyAuthority('GAME_EDIT')")
    public ResponseEntity<?> createGame(@RequestBody GameDTO model) {
        return gameService.create(model);
    }

    //edit Game api
    @PutMapping("/game/{id}")
    @PreAuthorize("hasAnyAuthority('GAME_EDIT')")
    public ResponseEntity<?> updateGame(@RequestBody GameDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return gameService.update(model);
    }

    //search by Id api
    @GetMapping("/game/{id}")
    @PreAuthorize("hasAnyAuthority('GAME_EDIT')")
    public ResponseEntity<?> getGame(@PathVariable Long id) {
        return gameService.getGame(id);
    }

    //search Game by placeId & paging api
    @GetMapping("/game/findByPlaceId")
    @PreAuthorize("hasAnyAuthority('GAME_EDIT')")
    public ResponseEntity<?> findByPlaceId(@RequestParam(value = "placeId", required = false) Long placeId,
                                           @RequestParam(value = "limit", required = false) Long limit,
                                           @RequestParam(value = "page", required = false) Long page) {
        return gameService.findByPlaceId(placeId, limit, page);
    }

    //get game by place api
    @GetMapping("/game/listOption")
    public ResponseEntity<?> listOptionByPlace(@RequestParam(value = "placeId", required = false) Long id){
        return gameService.listOptionByPlace(id);
    }

}
