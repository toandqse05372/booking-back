package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_payment")
@Getter
@Setter
public class Payment extends BaseEntity{
    private int totalPayment;
    private Date purchaseDay;

    //Bảng Payment qhe 1-1 với Order
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    //Bảng Payment qhe 1-n với PaymentMethods
    @OneToMany(mappedBy = "payment")
    private Set<PaymentMethods> paymentMethods = new HashSet<>();

}
