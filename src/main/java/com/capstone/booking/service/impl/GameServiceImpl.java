package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.GameConverter;
import com.capstone.booking.common.key.MonoStatus;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.entity.dto.GameDTOLite;
import com.capstone.booking.repository.GameRepository;
import com.capstone.booking.repository.PlaceRepository;
import com.capstone.booking.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameConverter gameConverter;

    @Autowired
    private PlaceRepository placeRepository;


    //add
    @Override
    public ResponseEntity<?> create(GameDTO gameDTO) {
        Place place = placeRepository.findById(gameDTO.getPlaceId()).get();
        if (place == null) {
            return new ResponseEntity("PLACE_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        if (gameRepository.findByGameNameAndPlace(gameDTO.getGameName(), place) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GAME_EXISTED");
        }
        Game game = gameConverter.toGame(gameDTO);
        game.setPlace(place);
        game.setStatus(MonoStatus.ACTIVE.toString());
        game = gameRepository.save(game);
        return ResponseEntity.ok(gameConverter.toDTO(game));
    }

    //edit
    @Override
    public ResponseEntity<?> update(GameDTO gameDTO) {
        Place place = placeRepository.findById(gameDTO.getPlaceId()).get();
        if (place == null) {
            return new ResponseEntity("PLACE_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        Game existedGame = gameRepository.findByGameNameAndPlace(gameDTO.getGameName(), place);
        if (existedGame != null && existedGame.getId() != gameDTO.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GAME_EXISTED");
        }
        Game game = new Game();
        Optional<Game> gameOptional = gameRepository.findById(gameDTO.getId());
        Game oldGame = gameOptional.get();
        game = gameConverter.toGame(gameDTO, oldGame);
        game.setPlace(place);
        game = gameRepository.save(game);
        return ResponseEntity.ok(gameConverter.toDTO(game));
    }


    //delete Game
    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        if (!gameRepository.findById(id).isPresent()) {
            return new ResponseEntity("GAME_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        gameRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }


    //search by Id
    @Override
    public ResponseEntity<?> getGame(Long id) {
        Optional<Game> games = gameRepository.findById(id);
        Game game = games.get();
        return ResponseEntity.ok(gameConverter.toDTO(game));
    }


    //search Game by name & placeName, & paging
    @Override
    public ResponseEntity<?> findByMulParam(String gameName, String placeName, Long limit, Long page) {
        Output results = gameRepository.findByMulParam(gameName, placeName, limit, page);
        return ResponseEntity.ok(results);
    }

    //get all game from active places
    @Override
    public ResponseEntity<?> findAll() {
        List<GameDTO> results = new ArrayList<>();
        List<Game> games = gameRepository.findAll();
        for (Game item : games) {
            GameDTO gameDTO = gameConverter.toDTO(item);
            results.add(gameDTO);
        }

        return ResponseEntity.ok(results);
    }

    //change status of game
    @Override
    public ResponseEntity<?> changeStatus(Long id) {
        Game game = gameRepository.findById(id).get();
        if (game.getStatus().equals(MonoStatus.ACTIVE.toString())) {
            game.setStatus(MonoStatus.DEACTIVATE.toString());
        } else {
            game.setStatus(MonoStatus.ACTIVE.toString());
        }
        game = gameRepository.save(game);
        return ResponseEntity.ok(gameConverter.toDTO(game));
    }

    //search Game by placeId & paging
    @Override
    public ResponseEntity<?> findByPlaceId(Long placeId, Long limit, Long page) {
        Output results = gameRepository.findByPlaceId(placeId, limit, page);
        return ResponseEntity.ok(results);
    }

    // get all game by place id
    @Override
    public ResponseEntity<?> listOptionByPlace(long id) {
        List<Game> gameList = gameRepository.findByPlaceIdAndStatus(id, MonoStatus.ACTIVE.toString());
        List<GameDTOLite> liteList = new ArrayList<>();
        for (Game game : gameList) {
            GameDTOLite lite = gameConverter.toGameLite(game);
            liteList.add(lite);
        }
        return ResponseEntity.ok(liteList);
    }

}
