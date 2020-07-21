package com.capstone.booking.service;

import com.capstone.booking.common.converter.GameConverter;
import com.capstone.booking.entity.Game;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class GameServiceTest {

    private String existedgame = "Hà Nội";
    private String existedgame2 = "Hà Nội 9";
    private String newgame_add = "Hà Nội 69";
    private String newgame_add_to_read = "Hà Nội 691";
    private String newgame_update = "new_game";
    private Long existedGameId = 2l;

    @Autowired
    GameService gameService;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameConverter gameConverter;

    //test if service is ready
    @Test
    void serviceNotNullTests(){ assertNotNull(gameService); }

    //create new game with new name, return new game
    @Test
    void create_game_with_new_name() {
        GameDTO dto = new GameDTO();
        dto.setGameName(newgame_add);
        ResponseEntity entity = gameService.create(dto);
        GameDTO result = (GameDTO) entity.getBody();
        assertEquals(dto.getGameName(), result.getGameName());
    }

    //create new game with existed name, catch error
    @Test
    void create_game_with_existed_name() {
        GameDTO dto = new GameDTO();
        dto.setGameName(existedgame);
        ResponseEntity entity = gameService.create(dto);
        assertEquals("GAME_EXISTED", entity.getBody().toString());
    }

    //create old game with existed name, catch error
    @Test
    void update_game_with_existed_name(){
        Game game = gameRepository.findById(existedGameId).get();
        GameDTO existedResponse = gameConverter.toDTO(game);
        existedResponse.setGameName(existedgame2);
        ResponseEntity entity = gameService.update(existedResponse);
        assertEquals("game_EXISTED", entity.getBody().toString());
    }

    //read game existed
    @Test
    void read_game_existed(){
        Game game = new Game();
        game.setGameName(newgame_add);
        Game saved = gameRepository.save(game);
        ResponseEntity entity = gameService.getGame(saved.getId());
        GameDTO dto = (GameDTO) entity.getBody();
        assertEquals(newgame_add_to_read, dto.getGameName());
    }

    //update old game with existed name, catch error
    @Test
    void update_game_with_new_name(){
        Game game = gameRepository.findById(existedGameId).get();
        GameDTO existedResponse = gameConverter.toDTO(game);
        existedResponse.setGameName(newgame_update);
        ResponseEntity entity = gameService.update(existedResponse);
        GameDTO updatedgame = (GameDTO) entity.getBody();
        assertEquals(newgame_update, updatedgame.getGameName());
    }

    //delete existed game, return null
    @Test
    void delete_game_with_existed_game(){
        gameService.delete(existedGameId);
        assertNull(gameRepository.findById(existedGameId).get());
    }

    //delete not existed game, catch error
    @Test
    void delete_game_with_not_existed_game(){
        ResponseEntity entity = gameService.delete(-1);
        assertEquals("GAME_NOT_FOUND", entity.getBody().toString());
    }

}