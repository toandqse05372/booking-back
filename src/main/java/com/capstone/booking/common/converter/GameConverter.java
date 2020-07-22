package com.capstone.booking.common.converter;

import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.entity.dto.GameDTOLite;
import com.capstone.booking.entity.dto.PlaceDTOLite;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

//convert game
@Component
public class GameConverter {

    //convert from dto to entity (for add)
    public Game toGame(GameDTO dto) {
        Game game = new Game();
        game.setGameName(dto.getGameName());
        game.setGameDescription(dto.getGameDescription());
        return game;
    }

    //convert from dto to entity (for update)
    public Game toGame(GameDTO dto, Game game) {
        game.setGameName(dto.getGameName());
        game.setGameDescription(dto.getGameDescription());
        return game;
    }

    //convert from entity to dto
    public GameDTO toDTO(Game game) {
        GameDTO dto = new GameDTO();
        if (game.getId() != null) {
            dto.setId(game.getId());
        }
        dto.setGameName(game.getGameName());
        dto.setGameDescription(game.getGameDescription());

//        Set<TicketType> typeSet = game.getTicketTypes();
//        Set<String> typeString = new HashSet<>();
//        if(typeSet != null){
//            for (TicketType ticketType : typeSet) {
//                typeString.add(ticketType.getTypeName());
//            }
//        }
//        dto.setTicketTypeName(typeString);

        if (game.getPlace() != null) {
            dto.setPlaceId(game.getPlace().getId());
            dto.setPlaceName(game.getPlace().getName());
        }

        dto.setStatus(game.getStatus());

        return dto;
    }

    //convert from game to dto (id and name only)
    public GameDTOLite toGameLite(Game game){
        GameDTOLite lite = new GameDTOLite();
        lite.setId(game.getId());
        lite.setGameName(game.getGameName());
        return lite;
    }
}
