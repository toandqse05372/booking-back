package com.capstone.booking.service;

import com.capstone.booking.entity.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {
    //find all
    ResponseEntity<?> getAllCategories();

    //delete
    ResponseEntity<?> delete(long id);

    //add
    ResponseEntity<?> create(CategoryDTO categoryDTO, MultipartFile file);

    //edit
    ResponseEntity<?> update(CategoryDTO categoryDTO, MultipartFile file);

    //search category by categoryName & paging
    ResponseEntity<?> findByTypeName(String typeName, Long limit, Long page);

    //search by Id
    ResponseEntity<?> getCategory(Long id);
}
