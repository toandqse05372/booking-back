package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "t_visitor_type")
public class VisitorType extends BaseEntity{
    private String typeName;
    private String typeKey;
    private int price;


    //Bảng TicketType qhe 1-n với VisitorType
    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

    //Bảng VisitorType qhe 1-n với Code
    @OneToMany(mappedBy = "visitorType")
    private Set<Code> code;
}
