package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_code", uniqueConstraints={ @UniqueConstraint(columnNames = {"visitor_type_id", "code"})})
public class Code extends BaseEntity{
    @Column(length = 50)
    private String code;
    //Bảng VisitorType qhe 1-n với Code
    @ManyToOne
    @JoinColumn(name = "visitor_type_id")
    private VisitorType visitorType;
}
