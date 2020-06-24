package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Game;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.entity.dto.TicketTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TicketTypeConverter {

    @Autowired
    GameConverter gameConverter;

    public TicketType toTicketType(TicketTypeDTO dto) {
        TicketType ticketType = new TicketType();
        ticketType.setTypeName(dto.getTypeName());
        ticketType.setTicketDescription(dto.getTicketDescription());
        ticketType.setReservationInfo(dto.getReservationInfo());
        ticketType.setCancelPolicy(dto.getCancelPolicy());
        ticketType.setConversionMethod(dto.getConversionMethod());
        return ticketType;
    }

    public TicketType toTicketType(TicketTypeDTO dto, TicketType ticketType) {
        ticketType.setTypeName(dto.getTypeName());
        ticketType.setTicketDescription(dto.getTicketDescription());
        ticketType.setReservationInfo(dto.getReservationInfo());
        ticketType.setCancelPolicy(dto.getCancelPolicy());
        ticketType.setConversionMethod(dto.getConversionMethod());
        return ticketType;
    }

    public TicketTypeDTO toDTO(TicketType ticketType) {
        TicketTypeDTO dto = new TicketTypeDTO();
        if (ticketType.getId() != null) {
            dto.setId(ticketType.getId());
        }
        dto.setTypeName(ticketType.getTypeName());
        dto.setTicketDescription(ticketType.getTicketDescription());
        dto.setReservationInfo(ticketType.getReservationInfo());
        dto.setCancelPolicy(ticketType.getCancelPolicy());
        dto.setConversionMethod(ticketType.getConversionMethod());

        Set<Game> gameSet = ticketType.getGame();
        Set<String> gameString = new HashSet<>();
        Set<GameDTO> gameDTOSet = new HashSet<>();
        for (Game game : gameSet) {
            gameDTOSet.add(gameConverter.toDTO(game));
        }
        dto.setGame(gameDTOSet);
        return dto;
    }
}
