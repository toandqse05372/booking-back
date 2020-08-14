package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "t_visitor_type")
public class VisitorType extends BaseEntity{
    @Column(length = 50)
    private String typeName;
    @Column(length = 50)
    private String typeKey;
    private int price;
    private boolean isBasicType = false;
    private String status;

    // TicketType  1-n relationship with VisitorType
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

    //VisitorType  1-n relationship with orderItem
    @OneToMany(mappedBy = "visitorType", fetch = FetchType.LAZY)
    private Set<OrderItem> orderItem;

//    //Bảng VisitorType qhe 1-n với Code
//    @OneToMany(mappedBy = "visitorType", fetch = FetchType.LAZY)
//    private Set<Code> code;
}
