package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Game;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.entity.dto.TicketTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

//convert ticket type
@Component
public class TicketTypeConverter {

    @Autowired
    GameConverter gameConverter;

    //convert from dto to entity (for add)
    public TicketType toTicketType(TicketTypeDTO dto) {
        TicketType ticketType = new TicketType();
        ticketType.setTypeName(dto.getTypeName());
        ticketType.setPlaceId(dto.getPlaceId());
        return ticketType;
    }

    //convert from dto to entity (for update)
    public TicketType toTicketType(TicketTypeDTO dto, TicketType ticketType) {
        ticketType.setTypeName(dto.getTypeName());
        ticketType.setPlaceId(dto.getPlaceId());
        return ticketType;
    }

    //convert from entity to dto
    public TicketTypeDTO toDTO(TicketType ticketType) {
        TicketTypeDTO dto = new TicketTypeDTO();
        dto.setId(ticketType.getId());
        dto.setStatus(ticketType.getStatus());
        dto.setTypeName(ticketType.getTypeName());
        dto.setPlaceId(ticketType.getPlaceId());
        Set<Game> gameSet = ticketType.getGame();
        Set<Long> gameIdSet = new HashSet<>();
        for (Game game : gameSet) {
            gameIdSet.add(game.getId());
        }
        dto.setGameId(gameIdSet);
        return dto;
    }
}
