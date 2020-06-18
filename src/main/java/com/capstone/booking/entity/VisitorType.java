package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_visitor_type")
public class VisitorType extends BaseEntity{
    private String type;
    private int price;
    private String typeKey;


    //Bảng TicketType qhe 1-n với Age
    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;
}
