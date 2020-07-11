package com.capstone.booking.service;

import com.capstone.booking.entity.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {
    //find all
    ResponseEntity<?> getAllCategories();

    //xóa
    ResponseEntity<?> delete(long id);

    //them
    ResponseEntity<?> create(CategoryDTO categoryDTO, MultipartFile file);

    //sưa
    ResponseEntity<?> update(CategoryDTO categoryDTO, MultipartFile file);

    //tim kiem Category theo name  & paging
    ResponseEntity<?> findByTypeName(String typeName, Long limit, Long page);

    //search by Id
    ResponseEntity<?> getCategory(Long id);
}
