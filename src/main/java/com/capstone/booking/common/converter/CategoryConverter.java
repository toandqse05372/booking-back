package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.dto.CategoryDTO;
import org.springframework.stereotype.Component;

//convert category
@Component
public class CategoryConverter {
    //convert from entity to dto
    public CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setCategoryName(category.getTypeName());
        dto.setDescription(category.getDescription());
        dto.setTypeKey(category.getTypeKey());
        dto.setIconLink(category.getIconLink());
        return dto;
    }

    //convert from dto to entity (for add)
    public Category toCategory(CategoryDTO dto) {
        Category category = new Category();
        category.setTypeName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setTypeKey(dto.getTypeKey());
        return category;
    }

    //convert from dto to entity (for update)
    public Category toCategory(CategoryDTO dto, Category category) {
        category.setTypeName(dto.getCategoryName());
        category.setTypeKey(dto.getTypeKey());
        category.setDescription(dto.getDescription());
        return category;
    }

}
