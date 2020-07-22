package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CategoryConverter;
import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.dto.CategoryDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CategoryRepositoryImplTest {

    @Mock
    private CategoryConverter mockCategoryConverter;

    @InjectMocks
    private CategoryRepositoryImpl categoryRepositoryImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testFindByMulParam() {
        // Setup
        final Output expectedResult = new Output();
        expectedResult.setPage(0);
        expectedResult.setTotalPage(0);
        expectedResult.setListResult(Arrays.asList());
        expectedResult.setTotalItems(0);

        // Configure CategoryConverter.toDTO(...).
        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("categoryName");
        categoryDTO.setTypeKey("typeKey");
        categoryDTO.setIconLink("iconLink");
        when(mockCategoryConverter.toDTO(new Category("typeName", "typeKey"))).thenReturn(categoryDTO);

        // Run the test
        final Output result = categoryRepositoryImplUnderTest.findByMulParam("typeName", 0L, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testConvertList() {
        // Setup
        final List<Category> categories = Arrays.asList(new Category("typeName", "typeKey"));
        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("categoryName");
        categoryDTO.setTypeKey("typeKey");
        categoryDTO.setIconLink("iconLink");
        final List<CategoryDTO> expectedResult = Arrays.asList(categoryDTO);

        // Configure CategoryConverter.toDTO(...).
        final CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setCategoryName("categoryName");
        categoryDTO1.setTypeKey("typeKey");
        categoryDTO1.setIconLink("iconLink");
        when(mockCategoryConverter.toDTO(new Category("typeName", "typeKey"))).thenReturn(categoryDTO1);

        // Run the test
        final List<CategoryDTO> result = categoryRepositoryImplUnderTest.convertList(categories);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testQueryCategory() {
        // Setup
        final Map<String, Object> params = new HashMap<>();
        final List<Category> expectedResult = Arrays.asList(new Category("typeName", "typeKey"));

        // Run the test
        final List<Category> result = categoryRepositoryImplUnderTest.queryCategory(params, "sqlStr");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
