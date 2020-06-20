package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CategoryConverter;
import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.Game;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.dto.CategoryDTO;
import com.capstone.booking.repository.CategoryRepository;
import com.capstone.booking.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter categoryConverter;

    //get All
    @Override
    public ResponseEntity<?> getAllCategories() {
        List<CategoryDTO> results = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();

        for (Category item : categories) {
            CategoryDTO categoryDTO = categoryConverter.toDTO(item);
            results.add(categoryDTO);
        }
        return ResponseEntity.ok(results);
    }

    //xoa
    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    //thêm
    @Override
    public ResponseEntity<?> create(CategoryDTO categoryDTO) {
        Category category = categoryConverter.toCategory(categoryDTO);
        if (categoryRepository.findOneByTypeName(category.getTypeName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CATEGORY_EXISTED");
        }
        categoryRepository.save(category);
        return ResponseEntity.ok(categoryConverter.toDTO(category));
    }

    //sửa
    @Override
    public ResponseEntity<?> update(CategoryDTO categoryDTO) {
        Category category = new Category();
        Category categoryOld = categoryRepository.findById(categoryDTO.getId()).get();
        category = categoryConverter.toCategory(categoryDTO, categoryOld);
        if (categoryRepository.findOneByTypeName(category.getTypeName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CATEGORY_EXISTED");
        }
        categoryRepository.save(category);
        return ResponseEntity.ok(categoryConverter.toDTO(category));
    }

    @Override
    public ResponseEntity<?> findByMulParam(String typeName, Long limit, Long page) {
        Output results = categoryRepository.findByMulParam(typeName, limit, page);
        return ResponseEntity.ok(results);
    }

    //search by Id
    @Override
    public ResponseEntity<?> getCategory(Long id) {
        Category category = categoryRepository.findById(id).get();
        return ResponseEntity.ok(categoryConverter.toDTO(category));
    }

}
