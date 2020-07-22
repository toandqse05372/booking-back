package com.capstone.booking.api;

import com.capstone.booking.entity.dto.CategoryDTO;
import com.capstone.booking.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
        final ResponseEntity<?> result = categoryControllerUnderTest.searchByName("categoryName", 0L, 0L);

        // Verify the results
    }

    @Test
    public void testFindAllCategories() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCategoryService).getAllCategories();

        // Run the test
        final ResponseEntity<?> result = categoryControllerUnderTest.findAllCategories();

        // Verify the results
    }

    @Test
    public void testDelete() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCategoryService).delete(0L);

        // Run the test
        final ResponseEntity<?> result = categoryControllerUnderTest.delete(0L);

        // Verify the results
    }

    @Test
    public void testCreate() throws Exception {
        // Setup
        final MultipartFile file = null;
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCategoryService).create(eq(new CategoryDTO()), any(MultipartFile.class));

        // Run the test
        final ResponseEntity<?> result = categoryControllerUnderTest.create(file, "{\"id\":null,\"categoryName\":\"Khu vui chơi\",\"typeKey\":\"PARK\"}");

        // Verify the results
    }

    @Test
    public void testCreate_ThrowsJsonProcessingException() {
        // Setup
        final MultipartFile file = null;
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCategoryService).create(eq(new CategoryDTO()), any(MultipartFile.class));

        // Run the test
        assertThatThrownBy(() -> {
            categoryControllerUnderTest.create(file, "message");
        }).isInstanceOf(JsonProcessingException.class).hasMessageContaining("message");
    }

    @Test
    public void testUpdate() throws Exception {
        // Setup
        final MultipartFile file = null;
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCategoryService).update(eq(new CategoryDTO()), any(MultipartFile.class));

        // Run the test
        final ResponseEntity<?> result = categoryControllerUnderTest.update(file, "{\"id\":1,\"categoryName\":\"Khu vui chơi\",\"typeKey\":\"PARK\"}", 0L);

        // Verify the results
    }

    @Test
    public void testUpdate_ThrowsJsonProcessingException() {
        // Setup
        final MultipartFile file = null;
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCategoryService).update(eq(new CategoryDTO()), any(MultipartFile.class));

        // Run the test
        assertThatThrownBy(() -> {
            categoryControllerUnderTest.update(file, "message", 0L);
        }).isInstanceOf(JsonProcessingException.class).hasMessageContaining("message");
    }

    @Test
    public void testGetPlace() {
        // Setup
        doReturn(new ResponseEntity<>(null, HttpStatus.CONTINUE)).when(mockCategoryService).getCategory(0L);

        // Run the test
        final ResponseEntity<?> result = categoryControllerUnderTest.getPlace(0L);

        // Verify the results
    }
}
