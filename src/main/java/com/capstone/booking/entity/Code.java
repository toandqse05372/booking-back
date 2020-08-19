package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_code", uniqueConstraints={ @UniqueConstraint(columnNames = {"visitor_type_id", "code", "created_at"})})
public class Code extends BaseEntity{
    @Column(length = 50)
    private String code;
    private Date redemptionDate;
    //Bảng VisitorType qhe 1-n với Code
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_type_id")
    private VisitorType visitorType;
}
