package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode
public class CategoryDTO extends BaseDTO{
    private String typeName;
    private String typeKey;
    //private Set<String> placeName;
}
