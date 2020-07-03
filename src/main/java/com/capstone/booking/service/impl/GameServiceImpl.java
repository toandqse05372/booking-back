package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.GameConverter;
import com.capstone.booking.common.key.PlaceAndGameStatus;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.entity.dto.GameDTOLite;
import com.capstone.booking.entity.dto.PlaceDTOLite;
import com.capstone.booking.repository.GameRepository;
import com.capstone.booking.repository.PlaceRepository;
import com.capstone.booking.repository.TicketTypeRepository;
import com.capstone.booking.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameConverter gameConverter;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private PlaceRepository placeRepository;


    //create
    @Override
    public ResponseEntity<?> create(GameDTO gameDTO) {
        Game game = gameConverter.toGame(gameDTO);
        if (gameRepository.findByGameName(game.getGameName()) != null
                && gameRepository.findByPlaceId(gameDTO.getPlaceId()).size() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GAME_EXISTED");
        }

        Optional<Place> placeOptional = placeRepository.findById(gameDTO.getPlaceId());
        if (!placeOptional.isPresent()) {
            return new ResponseEntity("PLACE_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        game.setPlace(placeOptional.get());

        game.setStatus(PlaceAndGameStatus.ACTIVE.toString());

        game = gameRepository.save(game);
        return ResponseEntity.ok(gameConverter.toDTO(game));
    }

    //edit
    @Override
    public ResponseEntity<?> update(GameDTO gameDTO) {
        Game game = new Game();
        Game oldGame = gameRepository.findById(gameDTO.getId()).get();
        game = gameConverter.toGame(gameDTO, oldGame);

        if (gameRepository.findByGameName(game.getGameName()) != null
                && gameRepository.findByPlaceId(gameDTO.getPlaceId()).size() != 0) {
            if (gameRepository.findByGameName(game.getGameName()).getId() != game.getId()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GAME_EXISTED");
            }
        }

        Set<TicketType> typeSet = new HashSet<>();
        game.setTicketTypes(typeSet);

        Optional<Place> placeOptional = placeRepository.findById(gameDTO.getPlaceId());
        if (!placeOptional.isPresent()) {
            return new ResponseEntity("PLACE_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        game.setPlace(placeOptional.get());

        game = gameRepository.save(game);
        return ResponseEntity.ok(gameConverter.toDTO(game));
    }


    //delete GAme
    @Override
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


    //tim kiem Game theo name & placeName, & paging
    @Override
    public ResponseEntity<?> findByMulParam(String gameName, String placeName, Long limit, Long page) {
        Output results = gameRepository.findByMulParam(gameName, placeName, limit, page);
        return ResponseEntity.ok(results);
    }

    //getAllGame  //game nào mà place còn active thì mới select
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

    //change status
    @Override
    public ResponseEntity<?> changeStatus(Long id) {
        Game game = gameRepository.findById(id).get();
        if (game.getStatus().equals(PlaceAndGameStatus.ACTIVE.toString())) {
            game.setStatus(PlaceAndGameStatus.DEACTIVATE.toString());
        } else {
            game.setStatus(PlaceAndGameStatus.ACTIVE.toString());
        }
        game = gameRepository.save(game);
        return ResponseEntity.ok(gameConverter.toDTO(game));
    }

    //tim kiem Game theo placeId & paging
    @Override
    public ResponseEntity<?> findByPlaceId(Long placeId, Long limit, Long page) {
        Output results = gameRepository.findByPlaceId(placeId, limit, page);
        return ResponseEntity.ok(results);
    }

    @Override
    public ResponseEntity<?> listOptionByPlace(long id) {
        List<Game> gameList = gameRepository.findByPlaceId(id);
        List<GameDTOLite> liteList = new ArrayList<>();
        for (Game game : gameList) {
            GameDTOLite lite = gameConverter.toGameLite(game);
            liteList.add(lite);
        }
        return ResponseEntity.ok(liteList);
    }

}
