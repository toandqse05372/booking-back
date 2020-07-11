package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "t_ticket")
@Getter
@Setter
public class Ticket extends BaseEntity{
    @Column(length = 50)
    private String code;
    private Date redemptionDate;


    //Bảng Order qhe 1-n với Ticket
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    //Bảng Ticket qhe 1-n với VisitorType
    @OneToMany(mappedBy = "ticket")
    private Set<VisitorType> visitorType;

}
