package com.capstone.booking.service.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CategoryConverter;
import com.capstone.booking.config.aws.AmazonS3ClientService;
import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.dto.CategoryDTO;
import com.capstone.booking.repository.CategoryRepository;
import lombok.SneakyThrows;
import org.apache.poi.util.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository mockCategoryRepository;

    @Mock
    private CategoryConverter mockCategoryConverter;

    @Mock
    private AmazonS3ClientService mockAmazonS3ClientService;

    @InjectMocks
    private CategoryServiceImpl categoryServiceImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testGetAllCategories() {
        // Setup

        // Configure CategoryRepository.findAll(...).
        final List<Category> categories = Arrays.asList(new Category("typeName", "typeKey", "description"));
        when(mockCategoryRepository.findAll()).thenReturn(categories);

        // Configure CategoryConverter.toDTO(...).
        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("categoryName");
        categoryDTO.setTypeKey("typeKey");
        categoryDTO.setIconLink("iconLink");
        when(mockCategoryConverter.toDTO(new Category("typeName", "typeKey", "description"))).thenReturn(categoryDTO);

        // Run the test
        final ResponseEntity<?> result = categoryServiceImplUnderTest.getAllCategories();

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        // Setup

        // Configure CategoryRepository.findById(...).
        final Optional<Category> category = Optional.of(new Category("typeName", "typeKey", "description"));
        when(mockCategoryRepository.findById(0L)).thenReturn(category);

        // Run the test
        final ResponseEntity<?> result = categoryServiceImplUnderTest.delete(0L);

        // Verify the results
        verify(mockCategoryRepository).deleteById(0L);
    }

    @SneakyThrows
    @Test
    public void testCreate() {
        // Setup
        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("categoryName");
        categoryDTO.setTypeKey("typeKey");
        categoryDTO.setIconLink("iconLink");
        when(mockCategoryRepository.findByTypeName("typeName")).thenReturn(new Category("typeName", "typeKey", "description"));
        when(mockCategoryConverter.toCategory(categoryDTO)).thenReturn(new Category("typeName", "typeKey", "description"));
        when(mockCategoryRepository.save(new Category("typeName", "typeKey", "description"))).thenReturn(new Category("typeName", "typeKey", "description"));

        File file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        // Run the test
        final ResponseEntity<?> result = categoryServiceImplUnderTest.create(categoryDTO, multipartFile);

        // Verify the results
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(null), eq(multipartFile), eq("Category_null"), eq(".pdf"), eq(true));
    }

    @Test
    public void testFindByTypeName() {
        // Setup

        // Configure CategoryRepository.findByMulParam(...).
        final Output output = new Output();
        output.setPage(0);
        output.setTotalPage(0);
        output.setListResult(Arrays.asList());
        output.setTotalItems(0);
        when(mockCategoryRepository.findByMulParam("typeName", 0L, 0L)).thenReturn(output);

        // Run the test
        final ResponseEntity<?> result = categoryServiceImplUnderTest.findByTypeName("typeName", 0L, 0L);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testGetCategory() {
        // Setup

        // Configure CategoryRepository.findById(...).
        final Optional<Category> category = Optional.of(new Category("typeName", "typeKey", "description"));
        when(mockCategoryRepository.findById(0L)).thenReturn(category);

        // Configure CategoryConverter.toDTO(...).
        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("categoryName");
        categoryDTO.setTypeKey("typeKey");
        categoryDTO.setIconLink("iconLink");
        when(mockCategoryConverter.toDTO(new Category("typeName", "typeKey", "description" ))).thenReturn(categoryDTO);

        // Run the test
        final ResponseEntity<?> result = categoryServiceImplUnderTest.getCategory(0L);

        // Verify the results
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }

    @SneakyThrows
    @Test
    public void testUploadFile() {
        // Setup
        File file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        // Run the test
        final String result = categoryServiceImplUnderTest.uploadFile(multipartFile, 0L);

        // Verify the results
        assertThat(result).isEqualTo("nullCategory_0.pdf");
        verify(mockAmazonS3ClientService).uploadFileToS3Bucket(eq(0L), eq(multipartFile), eq("Category_0"), eq(".pdf"), eq(true));
    }
}
