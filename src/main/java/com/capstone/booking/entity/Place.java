package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_place")
@Getter
@Setter

public class Place extends BaseEntity {
    private String name;
    private String address;
    @Column(length = 10000)
    private String description;
    private String mail;
    private String phoneNumber;
    private String status;

    //Bảng Place qhe 1-n với OpeningHours
    @OneToMany(mappedBy = "place")
    private Set<OpeningHours> openingHours = new HashSet<>();

    //Bảng Place qhe n-n với category
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "t_place_category",
            joinColumns = {@JoinColumn(name = "place_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> categories = new HashSet<>();

    //Bảng City qhe 1-n với Place
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    //Bảng Place qhe 1-n với Image
    @OneToMany(mappedBy = "place", fetch = FetchType.EAGER)
    private Set<ImagePlace> imagePlace;

    //Bảng Place qhe 1-n với GAme
    @OneToMany(mappedBy = "place", fetch = FetchType.EAGER)
    private Set<Game> game;

}
