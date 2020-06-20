package com.capstone.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//ủy quyền
@Entity
@Table(name = "t_permission")
@Getter
@Setter
public class Permission extends BaseEntity {
    @Column(length = 50)
    private String permissionKey;
    @Column(length = 50)
    private String permissionName;

}
