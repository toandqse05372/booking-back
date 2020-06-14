package com.capstone.booking.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_park_type")
@Getter
@Setter
@Data
public class ParkType extends BaseEntity{
    private String typeName;
    private String typeKey;

    //Bảng Park qhe n-n với ParkType
    @ManyToMany(mappedBy = "parkTypes")
    private Set<Park> parks = new HashSet<>();
}
