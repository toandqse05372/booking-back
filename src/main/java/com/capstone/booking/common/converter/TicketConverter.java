package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Ticket;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.dto.TicketDTO;
import com.capstone.booking.entity.dto.TicketTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//convert ticket ( not use )
@Component
public class TicketConverter {

    public Ticket toTicket(TicketDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setCode(dto.getCode());
        ticket.setRedemptionDate(dto.getRedemptionDate());
        ticket.setVisitorTypeId(dto.getVisitorTypeId());
        return ticket;
    }

    public Ticket toTicket(TicketDTO dto, Ticket ticket) {
        ticket.setCode(dto.getCode());
        ticket.setRedemptionDate(dto.getRedemptionDate());
        ticket.setVisitorTypeId(dto.getVisitorTypeId());
        return ticket;
    }

    public TicketDTO toDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setCode(ticket.getCode());
        dto.setRedemptionDate(ticket.getRedemptionDate());
        dto.setVisitorTypeId(ticket.getVisitorTypeId());
        return dto;
    }
}
