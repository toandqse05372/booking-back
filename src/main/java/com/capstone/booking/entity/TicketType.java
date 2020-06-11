package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_ticket_type")
@Getter
@Setter
public class TicketType extends BaseEntity{
    private String typeName;
    private String effectiveTime;
    private String ticketDescription;
    private String redemptionDate;
    private String reservationInfo;
    private String cancelPolicy;
    private String conversionMethod;
    private int price;
    private String userObject;


    //Bảng ticketType qhe 1-n với Ticket
    @OneToMany(mappedBy = "ticketType", fetch = FetchType.EAGER)
    private Set<Ticket> ticket;


    //Bảng GAme qhe n-n với ticketType
    @ManyToMany(mappedBy = "ticketTypes")
    private Set<Game> game = new HashSet<>();


}
