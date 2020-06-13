package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "t_ticket")
@Getter
@Setter
public class Ticket extends BaseEntity{
    private String redemptionDate;
    private String form;
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
