package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "t_ticket")
@Getter
@Setter
public class Ticket extends BaseEntity{
    private String form;
    private String code;

    //Bảng Park qhe 1-n với Ticket
    @ManyToOne
    @JoinColumn(name = "park_id")
    private Park park;

    //Bảng Cart qhe 1-n với Ticket
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    //Bảng Ticket qhe 1-1 với TicketType
    @OneToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

}
