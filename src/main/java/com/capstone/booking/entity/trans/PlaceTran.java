package com.capstone.booking.entity.trans;

import com.capstone.booking.entity.BaseEntity;
import com.capstone.booking.entity.City;
import com.capstone.booking.entity.Language;
import com.capstone.booking.entity.Place;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "t_place_trans")
public class PlaceTran extends BaseEntity {
    @Column(length = 50)
    private String name;
    @Length(max = 1000)
    private String shortDescription;
    @Length(max = 1000)
    private String detailDescription;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
}
