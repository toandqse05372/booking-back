package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_opening_hours")
@Getter
@Setter
public class OpeningHours extends BaseEntity{
    @Column(length = 100)
    private String weekDay;
    private Date openHours;
    private Date closeHours;

    //Bảng PLace qhe 1-n với OpeningHours
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
}
