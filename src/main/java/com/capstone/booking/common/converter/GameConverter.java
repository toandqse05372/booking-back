package com.capstone.booking.common.converter;

import com.capstone.booking.entity.City;
import com.capstone.booking.entity.Game;
import com.capstone.booking.entity.Park;
import com.capstone.booking.entity.dto.CityDTO;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.entity.dto.ParkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class GameConverter {

    @Autowired
    private ParkConverter parkConverter;

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
        dto.setTicketTypeName(game.getTicketType().getTypeName());

        return dto;
    }
}
