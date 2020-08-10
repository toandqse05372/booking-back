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
    @Column(length = 1000)
    private String password;
    @Column(length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;
    @Column(length = 50)
    private String mail;
    private Date dob;
    @Column(length = 20)
    private String phoneNumber;
    @Column(length = 20)
    private String status;
    private String userType;
    private String avatarLink;
    //nhiều user có nhiều trường, nhiều trường thuộc về nhiều user
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "t_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    //Bảng User qhe 1-n với Order
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Order> order = new HashSet<>();

}
