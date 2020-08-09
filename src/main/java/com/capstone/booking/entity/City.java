package com.capstone.booking.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "t_city")
public class City extends BaseEntity{
    @Column(length = 50)
    private String name;
    @Length(max = 1000)
    private String shortDescription;
    @Length(max = 1000)
    private String detailDescription;

    private String imageLink;

    //Bảng City qhe 1-n với Place
    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<Place> places = new HashSet<>();
}
