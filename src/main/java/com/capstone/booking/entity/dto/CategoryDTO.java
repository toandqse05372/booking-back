package com.capstone.booking.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CategoryDTO extends BaseDTO{
    private String categoryName;
    private String description;
    private String typeKey;
    private String iconLink;
}
