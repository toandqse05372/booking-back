package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.GameConverter;
import com.capstone.booking.common.key.Status;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.entity.dto.ImageDTO;
import com.capstone.booking.entity.dto.PlaceDTO;
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
        //ntn thì trung nhau ???
        if (gameRepository.findByGameName(game.getGameName()) != null
                && gameRepository.findByPlaceId(game.getPlace().getId()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GAME_EXISTED");
        }

        Optional<Place> placeOptional = placeRepository.findById(gameDTO.getPlaceId());
        if (placeOptional.isPresent()) {
            game.setPlace(placeOptional.get());
        }

        game.setStatus(Status.ACTIVE.toString());

        game = gameRepository.save(game);
        return ResponseEntity.ok(gameConverter.toDTO(game));
    }

    //edit
    @Override
    public ResponseEntity<?> update(GameDTO gameDTO) {
        Game game = new Game();
        Game oldGame = gameRepository.findById(gameDTO.getId()).get();
        game = gameConverter.toGame(gameDTO, oldGame);
//        if (gameRepository.findByGameName(game.getGameName()) != null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GAME_EXISTED");
//        }
        Set<TicketType> typeSet = new HashSet<>();
        game.setTicketTypes(typeSet);

        Optional<Place> placeOptional = placeRepository.findById(gameDTO.getPlaceId());
        if (placeOptional.isPresent()) {
            game.setPlace(placeOptional.get());
        }
        game = gameRepository.save(game);
        return ResponseEntity.ok(gameConverter.toDTO(game));
    }


    //delete GAme
    @Override
    public void delete(long id) {
        gameRepository.deleteById(id);
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
        if (game.getStatus().equals(Status.ACTIVE.toString())) {
            game.setStatus(Status.DEACTIVATE.toString());
        } else {
            game.setStatus(Status.ACTIVE.toString());
        }
        game = gameRepository.save(game);
        return ResponseEntity.ok(gameConverter.toDTO(game));
    }

}
