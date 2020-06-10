package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_cart")
@Setter
@Getter
public class Cart extends BaseEntity{
    private int totalPayment;

    //Bảng User qhe 1-n với Cart
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //Bảng Cart qhe 1-n với Ticket
    @OneToMany(mappedBy = "cart")
    Set<Ticket> ticket = new HashSet<>();
}
