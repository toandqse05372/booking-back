package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_order")
@Setter
@Getter
public class Order extends BaseEntity{
    private int totalTicket;
    private int ticketTypeId;
    private String firstName;
    private String lastName;
    private String mail;
    private String phoneNumber;
    private String status;


    //Bảng User qhe 1-n với Order
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //Bảng Order qhe 1-n với Ticket
    @OneToMany(mappedBy = "order")
    Set<Ticket> ticket = new HashSet<>();

    //Bảng Payment qhe 1-1 với Order
    @OneToOne(mappedBy = "order")
    private Payment payment;
}
