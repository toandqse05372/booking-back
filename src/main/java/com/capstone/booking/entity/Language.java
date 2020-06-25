package com.capstone.booking.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_language")
public class Language extends BaseEntity {

    public Language() {
    }

    public Language(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Column(length = 50)
    private String name;
    @Length(max = 50)
    private String code;
}
