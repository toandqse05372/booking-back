package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.dto.CategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        if (category.getId() != null) {
            dto.setId(category.getId());
        }
        dto.setCategoryName(category.getTypeName());
        dto.setTypeKey(category.getTypeKey());
//        Set<Place> placeSet = category.getplaces();
//        Set<String> placeString = new HashSet<>();
//        for (Place place : placeSet) {
//            placeString.add(place.getName());
//        }
//        dto.setplaceName(placeString);
        return dto;
    }

    public Category toCategory(CategoryDTO dto) {
        Category category = new Category();
        category.setTypeName(dto.getCategoryName());
        category.setTypeKey(dto.getTypeKey());
        return category;
    }

    public Category toCategory(CategoryDTO dto, Category category) {
        category.setTypeName(dto.getCategoryName());
        category.setTypeKey(dto.getTypeKey());
        return category;
    }

}