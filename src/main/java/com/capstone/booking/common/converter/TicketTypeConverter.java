package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Game;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.dto.TicketTypeDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TicketTypeConverter {

    public TicketType toTicketType(TicketTypeDTO dto) {
        TicketType ticketType = new TicketType();
        ticketType.setTypeKey(dto.getTypeKey());
        ticketType.setTypeName(dto.getTypeName());
        ticketType.setEffectiveTime(dto.getEffectiveTime());
        ticketType.setTicketDescription(dto.getTicketDescription());
        ticketType.setReservationInfo(dto.getReservationInfo());
        ticketType.setCancelPolicy(dto.getCancelPolicy());
        ticketType.setConversionMethod(dto.getConversionMethod());
        ticketType.setPrice(dto.getPrice());
        ticketType.setUserObject(dto.getUserObject());
        return ticketType;
    }

    public TicketType toTicketType(TicketTypeDTO dto, TicketType ticketType) {
        ticketType.setTypeKey(dto.getTypeKey());
        ticketType.setTypeName(dto.getTypeName());
        ticketType.setEffectiveTime(dto.getEffectiveTime());
        ticketType.setTicketDescription(dto.getTicketDescription());
        ticketType.setReservationInfo(dto.getReservationInfo());
        ticketType.setCancelPolicy(dto.getCancelPolicy());
        ticketType.setConversionMethod(dto.getConversionMethod());
        ticketType.setPrice(dto.getPrice());
        ticketType.setUserObject(dto.getUserObject());
        return ticketType;
    }

    public TicketTypeDTO toDTO(TicketType ticketType) {
        TicketTypeDTO dto = new TicketTypeDTO();
        if (ticketType.getId() != null) {
            dto.setId(ticketType.getId());
        }
        dto.setTypeKey(ticketType.getTypeKey());
        dto.setTypeName(ticketType.getTypeName());
        dto.setEffectiveTime(ticketType.getEffectiveTime());
        dto.setTicketDescription(ticketType.getTicketDescription());
        dto.setReservationInfo(ticketType.getReservationInfo());
        dto.setCancelPolicy(ticketType.getCancelPolicy());
        dto.setConversionMethod(ticketType.getConversionMethod());
        dto.setPrice(ticketType.getPrice());
        dto.setUserObject(ticketType.getUserObject());

        Set<Game> gameSet = ticketType.getGame();
        Set<String> gameString = new HashSet<>();
        for (Game game : gameSet) {
            gameString.add(game.getGameName());
        }
        dto.setGameName(gameString);
        return dto;
    }
}
