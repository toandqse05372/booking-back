package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_image")
@Getter
@Setter
public class Image extends BaseEntity{
    private String imageLink;

    //Bảng Park qhe 1-n với Image
    @ManyToOne
    @JoinColumn(name = "park_id")
    private Park park;
}
