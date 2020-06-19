package com.capstone.booking.service;

import com.capstone.booking.entity.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    //find all
    ResponseEntity<?> getAllCategories();

    //xóa
    void delete(Long id);

    //them
    ResponseEntity<?> create(CategoryDTO categoryDTO);

    //sưa
    ResponseEntity<?> update(CategoryDTO categoryDTO);

    //tim kiem Category theo name  & paging
    ResponseEntity<?> findByMulParam(String typeName, Long limit, Long page);

    ResponseEntity<?> getCategory(Long id);
}
