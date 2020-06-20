package com.capstone.booking.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_game")
@Data
public class Game extends BaseEntity{
    private String gameName;
    private String gameDescription;
    private String status;

    //Bảng Game qhe n-n với TicketType
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "t_game_ticketType",
            joinColumns = {@JoinColumn(name = "game_id")},
            inverseJoinColumns = {@JoinColumn(name = "ticketType_id")})
    private Set<TicketType> ticketTypes = new HashSet<>();


    //Bảng Place qhe 1-n với Image
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

}
