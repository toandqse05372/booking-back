package com.capstone.booking.common.helper;

import com.capstone.booking.entity.Ticket;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.VisitorType;
import lombok.Data;

import java.util.List;

@Data
public class PrintRequest {
    VisitorType visitorType;
    TicketType ticketType;
    List<Ticket> tickets;
}
