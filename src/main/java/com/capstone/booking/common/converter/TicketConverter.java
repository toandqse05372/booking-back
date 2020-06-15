package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Ticket;
import com.capstone.booking.entity.dto.TicketDTO;
import org.springframework.stereotype.Component;


@Component
public class TicketConverter {
    public Ticket toTicket(TicketDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setCode(dto.getCode());
        ticket.setRedemptionDate(dto.getRedemptionDate());
        return ticket;
    }

    public Ticket toTicket(TicketDTO dto, Ticket ticket) {
        ticket.setCode(dto.getCode());
        ticket.setRedemptionDate(dto.getRedemptionDate());
        return ticket;
    }

    public TicketDTO toDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        if (ticket.getId() != null) {
            dto.setId(ticket.getId());
        }
        dto.setCode(ticket.getCode());
        dto.setRedemptionDate(ticket.getRedemptionDate());

        //dto.setOrderId(ticket.getOrder().getId());
        dto.setTicketTypeId(ticket.getTicketType().getId());
        return dto;
    }
}
