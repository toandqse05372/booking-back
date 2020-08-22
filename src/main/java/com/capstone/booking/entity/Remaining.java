package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_remaining")
@Getter
@Setter
public class Remaining extends BaseEntity {
    private int total;
    private Date redemptionDate;
    private long visitorTypeId;
}
