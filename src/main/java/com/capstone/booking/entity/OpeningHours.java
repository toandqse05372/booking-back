package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_opening_hours")
@Getter
@Setter
public class OpeningHours extends BaseEntity{
    private String weekDay;
    private String openHours;
    private String closeHours;

    //Bảng Park qhe 1-n với OpeningHours
    @ManyToOne
    @JoinColumn(name = "park_id")
    private Park park;
}
