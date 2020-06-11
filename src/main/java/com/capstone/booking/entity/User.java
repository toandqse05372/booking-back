package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_user")
@Getter
@Setter
public class User extends BaseEntity {

    private String password;
    private String firstName;
    private String lastName;
    private String mail;
    private Date dob;
    private String phoneNumber;
    private String status;

    //nhiều user có nhiều trường, nhiều trường thuộc về nhiều user
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    //Bảng User qhe 1-n với Order
    @OneToMany(mappedBy = "user")
    private Set<Order> order = new HashSet<>();

}
