package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_payment_methods")
@Getter
@Setter
public class PaymentMethods extends BaseEntity{
    private String methodKey;

    //Bảng Payment qhe 1-n với PaymentMethods
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
