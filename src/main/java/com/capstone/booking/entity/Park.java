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

public class Park extends BaseEntity{
    private String name;
    private String address;
    private String description;
    private String mail;
    private String phoneNumber;


    //Bảng Park qhe 1-n với OpeningHours
    @OneToMany(mappedBy = "park")
    private Set<OpeningHours> openingHours = new HashSet<>();

    //Bảng Park qhe n-n với parkType
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

    //Bảng Park qhe 1-n với GAme
    @OneToMany(mappedBy = "park", fetch = FetchType.EAGER)
    private Set<Game> game;

}
