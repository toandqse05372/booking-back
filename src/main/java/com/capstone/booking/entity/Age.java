package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_age")
public class Age extends BaseEntity{
    private String age;
    private int price;
    private String typeKey;


    //Bảng TicketType qhe 1-n với Age
    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;
}
