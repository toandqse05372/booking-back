package com.capstone.booking.entity;

import lombok.Data;

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

    //Bảng City qhe 1-n với Park
    @OneToMany(mappedBy = "city")
    private Set<Park> park = new HashSet<>();
}
