package com.capstone.booking.common.converter;

import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class GameConverter {

    public Game toGame(GameDTO dto) {
        Game game = new Game();
        game.setGameName(dto.getGameName());
        game.setGameDescription(dto.getGameDescription());
        game.setTicketInventoryStatus(dto.isTicketInventoryStatus());
        return game;
    }

    public Game toGame(GameDTO dto, Game game) {
        game.setGameName(dto.getGameName());
        game.setGameDescription(dto.getGameDescription());
        game.setTicketInventoryStatus(dto.isTicketInventoryStatus());
        return game;
    }

    public GameDTO toDTO(Game game) {
        GameDTO dto = new GameDTO();
        if (game.getId() != null) {
            dto.setId(game.getId());
        }
        dto.setGameName(game.getGameName());
        dto.setGameDescription(game.getGameDescription());
        dto.setTicketInventoryStatus(game.isTicketInventoryStatus());

        Set<TicketType> typeSet = game.getTicketTypes();
        Set<String> typeString = new HashSet<>();
        for (TicketType ticketType : typeSet) {
            typeString.add(ticketType.getTypeName());
        }
        dto.setTicketTypeName(typeString);

        if (game.getPlace() != null) {
            dto.setPlaceId(game.getPlace().getId());
            dto.setPlaceName(game.getPlace().getName());
        }
        dto.setStatus(game.getStatus());

        return dto;
    }
}
