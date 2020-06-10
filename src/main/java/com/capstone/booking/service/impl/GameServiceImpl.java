package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.GameConverter;
import com.capstone.booking.common.converter.ParkConverter;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.entity.dto.ParkDTO;
import com.capstone.booking.repository.GameRepository;
import com.capstone.booking.repository.ParkRepository;
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
    private ParkRepository parkRepository;

    @Autowired
    private ParkConverter parkConverter;

    //create
    @Override
    public ResponseEntity<?> create(GameDTO gameDTO) {
        Game game = gameConverter.toGame(gameDTO);
        //ntn thì trung nhau ???
        if (gameRepository.findByGameName(game.getGameName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GAME_EXISTED");
        }
        TicketType ticketType = ticketTypeRepository.findOneByTypeName(gameDTO.getTicketTypeName());
        game.setTicketType(ticketType);
        game = gameRepository.save(game);
        return ResponseEntity.ok(gameConverter.toDTO(game));
    }

    //edit
    @Override
    public ResponseEntity<?> update(GameDTO gameDTO) {
        Game game = new Game();
        Game oldGame = gameRepository.findById(gameDTO.getId()).get();
        game = gameConverter.toGame(gameDTO, oldGame);
        if (gameRepository.findByGameName(game.getGameName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GAME_EXISTED");
        }
        TicketType ticketType = ticketTypeRepository.findOneByTypeName(gameDTO.getTicketTypeName());
        game.setTicketType(ticketType);
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

    //getAllGame
    @Override
    public ResponseEntity<?> findAll() {
        List<GameDTO> results = new ArrayList<>();
        List<Game> games = gameRepository.findAll();
        for (Game item : games) {
            GameDTO gameDTO = gameConverter.toDTO(item);

//            //lấy Park tư db List<Park>
//            List<Park> parkList = parkRepository.findByGames(item);
//
//            //từ park convert sang parkdto
//            Set<ParkDTO> parkSet = new HashSet<>();
//            for (Park park: parkList) {
//                ParkDTO parkDTO = parkConverter.toDTO(park);
//                parkSet.add(parkDTO); //add parkDTO vào set
//            }
//            gameDTO.setPark(parkSet); //add set vào parkdto

            results.add(gameDTO);
        }

        return ResponseEntity.ok(results);
    }

    //tim kiem Game theo name & parkId, & paging
    @Override
    public ResponseEntity<?> findByMulParam(String gameName, Long parkId, Long limit, Long page) {
        Output results = gameRepository.findByMulParam(gameName, parkId, limit, page);
        return ResponseEntity.ok(results);
    }
}
