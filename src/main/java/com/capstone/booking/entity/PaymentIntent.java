package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"oid"})})
@Entity
public class PaymentIntent {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String piId;

    private Long oid;
}
