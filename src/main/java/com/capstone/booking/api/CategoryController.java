package com.capstone.booking.api;

import com.capstone.booking.entity.dto.CategoryDTO;
import com.capstone.booking.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //tim kiem category theo name & paging
    @GetMapping("/category/searchByName")
    public ResponseEntity<?> searchMUL(@RequestParam(value = "categoryName", required = false) String categoryName,
                                       @RequestParam(value = "limit", required = false) Long limit,
                                       @RequestParam(value = "page", required = false) Long page) {
        return categoryService.findByMulParam(categoryName, limit, page);
    }


    //get All
    @GetMapping("/categories")
    public ResponseEntity<?> findAllCategories() {
        return categoryService.getAllCategories();
    }

    //xóa
    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        categoryService.delete(id);
        return new ResponseEntity("Delete Successful", HttpStatus.OK);
    }

    //them
    @PostMapping("/category")
    public ResponseEntity<?> create(@RequestBody CategoryDTO model) {
        return categoryService.create(model);
    }

    //sua
    @PutMapping("/category/{id}")
    public ResponseEntity<?> update(@RequestBody CategoryDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return categoryService.update(model);
    }

    //search By Id
    @GetMapping("/category/{id}")
    public ResponseEntity<?> getPlace(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }


}
