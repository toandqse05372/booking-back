package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_order", uniqueConstraints = { @UniqueConstraint(columnNames = {"orderCode"})})
@Setter
@Getter
public class Order extends BaseEntity{
    private Long ticketTypeId;
    @Column(length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;
    @Column(length = 50)
    private String mail;
    @Column(length = 20)
    private String phoneNumber;
    @Column(length = 20)
    private String status;
    @Column(length = 50)
    private String orderCode;
    private int totalPayment;
    private Date purchaseDay;
    private Date redemptionDate;
    private Long placeId;


    //Bảng User qhe 1-n với Order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //Bảng Order qhe 1-n với OrderItem
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    Set<OrderItem> orderItem;
}
