package com.capstone.booking.service.impl;

import com.capstone.booking.common.converter.TicketConverter;
import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.Ticket;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.dto.TicketDTO;
import com.capstone.booking.repository.*;
import com.capstone.booking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


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

    //add
    @Override
    public ResponseEntity<?> create(TicketDTO ticketDTO) {
        Ticket ticket = ticketConverter.toTicket(ticketDTO);

        ticketRepository.save(ticket);
        return ResponseEntity.ok(ticketConverter.toDTO(ticket));
    }

    //edit
    @Override
    public ResponseEntity<?> update(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        Ticket oldTicket = ticketRepository.findById(ticketDTO.getId()).get();
        ticket = ticketConverter.toTicket(ticketDTO, oldTicket);

        ticketRepository.save(ticket);
        return ResponseEntity.ok(ticketConverter.toDTO(ticket));
    }

    //delete
    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        if (!ticketRepository.findById(id).isPresent()) {
            return new ResponseEntity("TICKET_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        ticketRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }
}
