package com.capstone.booking.common.helper.pdf;

import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.Ticket;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.VisitorType;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PrintRequest {
    Place place;
    VisitorType visitorType;
    TicketType ticketType;
    List<Ticket> tickets;
    Date redemptionDate;
}
