package com.capstone.booking.api;

import com.capstone.booking.entity.dto.CategoryDTO;
import com.capstone.booking.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//category's api
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //search category by categoryName & paging
    @GetMapping("/category/searchByName")
    @PreAuthorize("hasAnyAuthority('CATEGORY_EDIT')")
    public ResponseEntity<?> searchByName(@RequestParam(value = "categoryName", required = false) String categoryName,
                                          @RequestParam(value = "limit", required = false) Long limit,
                                          @RequestParam(value = "page", required = false) Long page) {
        return categoryService.findByTypeName(categoryName, limit, page);
    }


    //get All
    @GetMapping("/categories")
    public ResponseEntity<?> findAllCategories() {
        return categoryService.getAllCategories();
    }

    //delete
    @DeleteMapping("/category/{id}")
    @PreAuthorize("hasAnyAuthority('CATEGORY_EDIT')")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return categoryService.delete(id);
    }

    //add
    @PostMapping("/category")
    @PreAuthorize("hasAnyAuthority('CATEGORY_EDIT')")
    public ResponseEntity<?> create(@RequestPart(value = "file", required = false) MultipartFile file,
                                    @RequestPart(value = "category") String model) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CategoryDTO categoryDTO = mapper.readValue(model, CategoryDTO.class);
        return categoryService.create(categoryDTO, file);
    }

    //edit
    @PutMapping("/category/{id}")
    @PreAuthorize("hasAnyAuthority('CATEGORY_EDIT')")
    public ResponseEntity<?> update(@RequestPart(value = "file", required = false) MultipartFile file,
                                    @RequestPart(value = "category") String model,
                                    @PathVariable("id") long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CategoryDTO categoryDTO = mapper.readValue(model, CategoryDTO.class);
        categoryDTO.setId(id);
        return categoryService.update(categoryDTO, file);
    }

    //search By Id
    @GetMapping("/category/{id}")
    @PreAuthorize("hasAnyAuthority('CATEGORY_EDIT')")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }


}
