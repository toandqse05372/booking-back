package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_category")
@Data
public class Category extends BaseEntity{

    public Category(){}

    public Category(String typeName, String typeKey) {
        this.typeKey = typeKey;
        this.typeName = typeName;
    }

    @Column(length = 50)
    private String typeName;
    @Column(length = 50)
    private String typeKey;

    //Bảng Place qhe n-n với Category
    @ManyToMany(mappedBy = "categories")
    private Set<Place> places = new HashSet<>();
}
