package com.capstone.booking.api;

import com.capstone.booking.entity.dto.CategoryDTO;
import com.capstone.booking.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.util.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class CategoryControllerTest {

    @Mock
    private CategoryService mockCategoryService;

    @InjectMocks
    private CategoryController categoryControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testSearchByName() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCategoryService).findByTypeName("typeName", 0L, 0L);

        // Run the test
        final ResponseEntity<?> result = categoryControllerUnderTest.searchByName("typeName", 0L, 0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testFindAllCategories() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCategoryService).getAllCategories();

        // Run the test
        final ResponseEntity<?> result = categoryControllerUnderTest.findAllCategories();

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCategoryService).delete(0L);

        // Run the test
        final ResponseEntity<?> result = categoryControllerUnderTest.delete(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testCreate() throws Exception {
        // Setup
        String model = "{\"id\":null,\"categoryName\":\"Khu vui chơi\",\"typeKey\":\"PARK\"}";
        ObjectMapper mapper = new ObjectMapper();
        CategoryDTO categoryDTO = mapper.readValue(model, CategoryDTO.class);
        File file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCategoryService).create(categoryDTO, multipartFile);

        // Run the test
        final ResponseEntity<?> result = categoryControllerUnderTest.create(multipartFile, model);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testUpdate() throws Exception {
        // Setup
        String model = "{\"id\":0,\"categoryName\":\"Khu vui chơi\",\"typeKey\":\"PARK\"}";
        ObjectMapper mapper = new ObjectMapper();
        CategoryDTO categoryDTO = mapper.readValue(model, CategoryDTO.class);
        File file = new File("Test.pdf");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCategoryService).update(categoryDTO, multipartFile);

        // Run the test
        final ResponseEntity<?> result = categoryControllerUnderTest.update(multipartFile, model, 0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testGetPlace() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCategoryService).getCategory(0L);

        // Run the test
        final ResponseEntity<?> result = categoryControllerUnderTest.getCategory(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }

    @Test
    public void testGetCategory() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCategoryService).getCategory(0L);

        // Run the test
        final ResponseEntity<?> result = categoryControllerUnderTest.getCategory(0L);

        // Verify the results
        Assertions.assertEquals(100, result.getStatusCodeValue());
    }
}
