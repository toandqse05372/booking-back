package com.capstone.booking.service.impl;

import com.capstone.booking.common.converter.TicketConverter;
import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.Ticket;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.dto.TicketDTO;
import com.capstone.booking.repository.*;
import com.capstone.booking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketConverter ticketConverter;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ResponseEntity<?> create(TicketDTO ticketDTO) {
        Ticket ticket = ticketConverter.toTicket(ticketDTO);

        TicketType ticketType = ticketTypeRepository.findById(ticketDTO.getTicketTypeId()).get();
        ticket.setTicketType(ticketType);

        ticketRepository.save(ticket);
        return ResponseEntity.ok(ticketConverter.toDTO(ticket));
    }

    @Override
    public ResponseEntity<?> update(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        Ticket oldTicket = ticketRepository.findById(ticketDTO.getId()).get();
        ticket = ticketConverter.toTicket(ticketDTO, oldTicket);


        TicketType ticketType = ticketTypeRepository.findById(ticketDTO.getTicketTypeId()).get();
        ticket.setTicketType(ticketType);

        ticketRepository.save(ticket);
        return ResponseEntity.ok(ticketConverter.toDTO(ticket));
    }

    @Override
    public void delete(long id) {
        ticketRepository.deleteById(id);
    }
}
