package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_code")
public class Code extends BaseEntity{
    private String code;

    //Bảng TicketType qhe 1-n với Code
    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

}
