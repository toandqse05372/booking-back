package com.capstone.booking.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_category")
@Data
public class Category extends BaseEntity{

    public Category(){}

    public Category(String typeName, String typeKey, String description) {
        this.typeKey = typeKey;
        this.typeName = typeName;
        this.description = description;
    }

    @Column(length = 50)
    private String typeName;
    @Column(length = 50)
    private String typeKey;
    private String description;
    private String iconLink;

    //Bảng Place qhe n-n với Category
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<Place> places = new HashSet<>();
}
