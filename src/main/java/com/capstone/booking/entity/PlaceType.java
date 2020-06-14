package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_place_type")
@Data
public class PlaceType extends BaseEntity{
    private String typeName;
    private String typeKey;

    //Bảng Place qhe n-n với PlaceType
    @ManyToMany(mappedBy = "placeTypes")
    private Set<Place> places = new HashSet<>();
}
