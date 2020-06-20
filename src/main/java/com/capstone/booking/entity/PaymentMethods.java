package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_payment_methods")
@Getter
@Setter
public class PaymentMethods extends BaseEntity{
    @Column(length = 100)
    private String methodName;
    @Column(length = 50)
    private String methodKey;
    @Column(length = 20)
    private String status;

    //Bảng Order qhe 1-n với PaymentMethods
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
