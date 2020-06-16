package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "t_city")
public class City extends BaseEntity{
    private String name;

    private String shortDescription;

    @Column(length = 10000)
    private String detailDescription;

    //Bảng City qhe 1-n với Place
    @OneToMany(mappedBy = "city")
    private Set<Place> places = new HashSet<>();
}
