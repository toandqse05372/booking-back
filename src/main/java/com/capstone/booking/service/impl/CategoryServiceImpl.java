package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CategoryConverter;
import com.capstone.booking.config.aws.AmazonS3ClientService;
import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.ImagePlace;
import com.capstone.booking.entity.dto.CategoryDTO;
import com.capstone.booking.repository.CategoryRepository;
import com.capstone.booking.service.CategoryService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Value("${aws.bucketLink}")
    private String bucketLink;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter categoryConverter;

    @Autowired
    private AmazonS3ClientService amazonS3ClientService;

    //get all categories
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

    //delete
    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        if (!categoryRepository.findById(id).isPresent()) {
            return new ResponseEntity("CATEGORY_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        categoryRepository.deleteById(id);
        return new ResponseEntity("DELETE_SUCCESSFUL", HttpStatus.OK);
    }

    //add new category
    @Override
    public ResponseEntity<?> create(CategoryDTO categoryDTO, MultipartFile file) {
        if (categoryRepository.findByTypeName(categoryDTO.getCategoryName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CATEGORY_EXISTED");
        }
        Category category = categoryConverter.toCategory(categoryDTO);
        if (file != null) {
            Category saved = categoryRepository.save(category);
            saved.setIconLink(uploadFile(file, saved.getId()));
            categoryRepository.save(saved);
        } else {
            categoryRepository.save(category);
        }
        return ResponseEntity.ok(categoryConverter.toDTO(category));
    }

    //edit
    @Override
    public ResponseEntity<?> update(CategoryDTO categoryDTO, MultipartFile file) {
        Category existedCategory = categoryRepository.findByTypeName(categoryDTO.getCategoryName());
        if (existedCategory != null && existedCategory.getId() != categoryDTO.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CATEGORY_EXISTED");
        }
        Category category = new Category();
        Optional<Category> categoryOptional = categoryRepository.findById(categoryDTO.getId());
        Category categoryOld = categoryOptional.get();
        category = categoryConverter.toCategory(categoryDTO, categoryOld);
        if (file != null) {
            Category saved = categoryRepository.save(category);
            saved.setIconLink(uploadFile(file, saved.getId()));
            categoryRepository.save(saved);
        } else {
            categoryRepository.save(category);
        }
        return ResponseEntity.ok(categoryConverter.toDTO(category));
    }

    //search category by categoryName & paging
    @Override
    public ResponseEntity<?> findByTypeName(String typeName, Long limit, Long page) {
        Output results = categoryRepository.findByMulParam(typeName, limit, page);
        return ResponseEntity.ok(results);
    }

    //search by Id
    @Override
    public ResponseEntity<?> getCategory(Long id) {
        Category category = categoryRepository.findById(id).get();
        return ResponseEntity.ok(categoryConverter.toDTO(category));
    }

    //upload category icon to s3
    public String uploadFile(MultipartFile file, Long categoryId) {
        String ext = "." + FilenameUtils.getExtension(file.getOriginalFilename());
        String name = "Category_" + categoryId;
        this.amazonS3ClientService.uploadFileToS3Bucket(categoryId, file, "Category_" + categoryId, ext, true);
        return bucketLink + name + ext;
    }

}
