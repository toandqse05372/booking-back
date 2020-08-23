package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class OrderToken extends BaseEntity{
    private long orderId;
    private Date tokenExpDate = new Date(System.currentTimeMillis() + 7200000);
}
