package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_order_item")
@Setter
@Getter
public class OrderItem extends BaseEntity{
    private int quantity;

    //Bảng VisitorType qhe 1-n với OrderItem
    @ManyToOne
    @JoinColumn(name = "visitor_type_id")
    private VisitorType visitorType;

    //Bảng Order qhe 1-n với OrderItem
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    //Bảng OrderItem qhe 1-n với Ticket
    @OneToMany(mappedBy = "orderItem", fetch = FetchType.LAZY)
    private Set<Ticket> ticket;
}
