package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_place")
@Getter
@Setter
public class Place extends BaseEntity {
    private String name;
    private String placeKey;
    private String address;
    @Length(max = 1000)
    private String detailDescription;
    @Length(max = 1000)
    private String shortDescription;
    @Column(length = 50)
    private String mail;
    @Column(length = 50)
    private String phoneNumber;
    @Column(length = 20)
    private String status;
    @Length(max = 1000)
    private String location;
    private String cancelPolicy;
    private Integer basicPrice;

    private String openingHours;//tu thu 2 den thu 7...
    private String weekDays;//1.2.3.4..

    //Bảng Place qhe n-n với category
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "t_place_category",
            joinColumns = {@JoinColumn(name = "place_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> categories = new HashSet<>();

    //Bảng City qhe 1-n với Place
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    //Bảng Place qhe 1-n với Image
    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private Set<ImagePlace> imagePlace;

    //Bảng Place qhe 1-n với GAme
    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private Set<Game> game;
}
