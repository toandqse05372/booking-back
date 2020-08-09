package com.capstone.booking.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private String status;

    //Bảng ticketType qhe 1-n với VisitorType
    @OneToMany(mappedBy = "ticketType", fetch = FetchType.LAZY)
    private Set<VisitorType> visitorType;

    //Bảng Game qhe n-n với ticketType
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinTable(name = "t_game_ticketType",
            joinColumns = {@JoinColumn(name = "ticketType_id")},
            inverseJoinColumns = {@JoinColumn(name = "game_id")})
    private Set<Game> game = new HashSet<>();


}
