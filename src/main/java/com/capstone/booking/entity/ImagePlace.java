package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_image_place")
@Getter
@Setter
public class ImagePlace extends BaseEntity{
    private String imageLink;

    private String imageName;

    //Bảng Place qhe 1-n với Image
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
}
