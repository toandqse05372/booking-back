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
    @Column(length = 10000)
    private String ticketDescription;
    private String reservationInfo;
    private String cancelPolicy;
    private String conversionMethod;
    private int price;
    private String userObject;


    //Bảng ticketType qhe 1-n với Ticket
    @OneToMany(mappedBy = "ticketType", fetch = FetchType.EAGER)
    private Set<Ticket> ticket;

    //Bảng ticketType qhe 1-n với Code
    @OneToMany(mappedBy = "ticketType")
    private Set<Code> code;

    //Bảng ticketType qhe 1-n với Age
    @OneToMany(mappedBy = "ticketType")
    private Set<Age> age;

    //Bảng GAme qhe n-n với ticketType
    @ManyToMany(mappedBy = "ticketTypes")
    private Set<Game> game = new HashSet<>();


}
