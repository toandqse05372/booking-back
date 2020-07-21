package com.capstone.booking.service;

import com.capstone.booking.common.converter.CategoryConverter;
import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.dto.CategoryDTO;
import com.capstone.booking.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CategoryServiceTest {

    private String existedcategory = "Hà Nội";
    private String existedcategory2 = "Hà Nội 9";
    private String newcategory_add = "Hà Nội 69";
    private String newcategory_add_to_read = "Hà Nội 691";
    private String newcategory_update = "new_category";

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryConverter categoryConverter;

    //test if service is ready
    @Test
    void serviceNotNullTests(){ assertNotNull(categoryService); }

    //create new category with new name, return new category
    @Test
    void create_category_with_new_name() {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryName(newcategory_add);
        ResponseEntity entity = categoryService.create(dto, null);
        CategoryDTO result = (CategoryDTO) entity.getBody();
        assertEquals(dto.getCategoryName(), result.getCategoryName());
    }

    //create new category with existed name, catch error
    @Test
    void create_category_with_existed_name() {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryName(existedcategory);
        ResponseEntity entity = categoryService.create(dto, null);
        assertEquals("category_EXISTED", entity.getBody().toString());
    }

    //create old category with existed name, catch error
    @Test
    void update_category_with_existed_name(){
        Category category = categoryRepository.findByTypeName(existedcategory);
        CategoryDTO existedResponse = categoryConverter.toDTO(category);
        existedResponse.setCategoryName(existedcategory2);
        ResponseEntity entity = categoryService.update(existedResponse, null);
        assertEquals("CATEGORY_EXISTED", entity.getBody().toString());
    }

    //read category existed
    @Test
    void read_category_existed(){
        Category category = new Category();
        category.setTypeName(newcategory_add);
        Category saved = categoryRepository.save(category);
        ResponseEntity entity = categoryService.getCategory(saved.getId());
        CategoryDTO dto = (CategoryDTO) entity.getBody();
        assertEquals(newcategory_add_to_read, dto.getCategoryName());
    }

    //update old category with existed name, catch error
    @Test
    void update_category_with_new_name(){
        Category category = categoryRepository.findByTypeName(existedcategory);
        CategoryDTO existedResponse = categoryConverter.toDTO(category);
        existedResponse.setCategoryName(newcategory_update);
        ResponseEntity entity = categoryService.update(existedResponse, null);
        CategoryDTO updatedcategory = (CategoryDTO) entity.getBody();
        assertEquals(newcategory_update, updatedcategory.getCategoryName());
    }

    //delete existed category, return null
    @Test
    void delete_category_with_existed_category(){
        Category category = categoryRepository.findByTypeName(existedcategory);
        categoryService.delete(category.getId());
        assertNull(categoryRepository.findByTypeName(existedcategory));
    }

    //delete not existed category, catch error
    @Test
    void delete_category_with_not_existed_category(){
        ResponseEntity entity = categoryService.delete(-1);
        assertEquals("CATEGORY_NOT_FOUND", entity.getBody().toString());
    }

}