package com.capstone.booking.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_game")
@Data
public class Game extends BaseEntity{
    @Column(length = 100)
    private String gameName;
    @Length(max = 1000)
    private String gameDescription;
    @Column(length = 20)
    private String status;

    //Bảng Game qhe n-n với TicketType
    @ManyToMany(mappedBy = "game", fetch = FetchType.LAZY)
    private Set<TicketType> ticketTypes = new HashSet<>();


    //Bảng Place qhe 1-n với Image
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

}
