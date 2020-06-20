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
    @Column(length = 10000)
    private String reservationInfo;
    @Column(length = 10000)
    private String cancelPolicy;
    @Column(length = 10000)
    private String conversionMethod;


    //Bảng ticketType qhe 1-n với Ticket
    @OneToMany(mappedBy = "ticketType", fetch = FetchType.EAGER)
    private Set<Ticket> ticket;

    //Bảng ticketType qhe 1-n với VisitorType
    @OneToMany(mappedBy = "ticketType")
    private Set<VisitorType> visitorType;

    //Bảng GAme qhe n-n với ticketType
    @ManyToMany(mappedBy = "ticketTypes")
    private Set<Game> game = new HashSet<>();


}
