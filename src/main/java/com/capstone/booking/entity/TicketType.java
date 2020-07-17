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
public class TicketType extends BaseEntity {
    private String typeName;
    private Long placeId;

    //Bảng ticketType qhe 1-n với VisitorType
    @OneToMany(mappedBy = "ticketType")
    private Set<VisitorType> visitorType;

    //Bảng GAme qhe n-n với ticketType
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "t_game_ticketType",
            joinColumns = {@JoinColumn(name = "ticketType_id")},
            inverseJoinColumns = {@JoinColumn(name = "game_id")})
    private Set<Game> game = new HashSet<>();


}
