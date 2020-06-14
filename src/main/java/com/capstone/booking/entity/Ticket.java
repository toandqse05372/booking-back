package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_ticket")
@Getter
@Setter
public class Ticket extends BaseEntity{
    private Date redemptionDate;
    private String code;

    //Bảng Order qhe 1-n với Ticket
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    //Bảng TicketType qhe 1-n với Ticket
    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

}
