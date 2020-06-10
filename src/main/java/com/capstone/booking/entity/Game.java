package com.capstone.booking.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_game")
@Getter
@Setter
@Data
public class Game extends BaseEntity{
    private String gameName;
    private String gameDescription;
    private boolean ticketInventoryStatus; //trang thai ve còn hay k

    //Bảng TicketType qhe 1-n với Game
    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

    //Bảng Park qhe 1-n với Image
    @ManyToOne
    @JoinColumn(name = "park_id")
    private Park park;

//    //Bảng Park qhe n-n với Game
//    @ManyToMany(mappedBy = "games")
//    private Set<Park> parks = new HashSet<>();
}
