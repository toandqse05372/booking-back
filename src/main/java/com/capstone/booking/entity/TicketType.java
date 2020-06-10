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

    //Bảng Ticket qhe 1-1 với ticketType
    @OneToOne(mappedBy = "ticketType")
    private Ticket ticket;

    //Bảng ticketType qhe 1-n với Game
    @OneToMany(mappedBy = "ticketType")
    private Set<Game> game = new HashSet<>();

}
