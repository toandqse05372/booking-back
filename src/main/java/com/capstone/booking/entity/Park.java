package com.capstone.booking.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_park")
@Getter
@Setter
//xóa park image
public class Park extends BaseEntity{
    private String name;
    private String address;
    private String description;
    private String mail;
    private String phoneNumber;


    //Bảng Park qhe 1-n với Ticket
    @OneToMany(mappedBy = "park")
    private Set<Ticket> ticket = new HashSet<>();

    //Bảng Park qhe 1-n với OpeningHours
    @OneToMany(mappedBy = "park")
    private Set<OpeningHours> openingHours = new HashSet<>();

    //Bảng Park qhe n-n với tickType
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "t_park_parkType",
            joinColumns = {@JoinColumn(name = "park_id")},
            inverseJoinColumns = {@JoinColumn(name = "parkType_id")})
    private Set<ParkType> parkTypes = new HashSet<>();

    //Bảng City qhe 1-n với Park
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    //Bảng Park qhe 1-n với Image
    @OneToMany(mappedBy = "park", fetch = FetchType.EAGER)
    private Set<Image> image;

    //Bảng Park qhe n-n với Game// SỬA================================
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "t_park_game",
            joinColumns = {@JoinColumn(name = "park_id")},
            inverseJoinColumns = {@JoinColumn(name = "game_id")})
    private Set<Game> games = new HashSet<>();

}
