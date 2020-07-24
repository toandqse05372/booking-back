package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.dto.CategoryDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CategoryRepositoryImplTest {

    @Mock
    private EntityManager mockEntityManager;

    private CategoryRepositoryImpl categoryRepositoryImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        categoryRepositoryImplUnderTest = new CategoryRepositoryImpl(mockEntityManager);
    }

    @Test
    public void testFindByMulParam() {
        // Setup
        final Output expectedResult = new Output();
        expectedResult.setPage(0);
        expectedResult.setTotalPage(0);
        expectedResult.setListResult(Arrays.asList());
        expectedResult.setTotalItems(0);

        when(mockEntityManager.createNativeQuery("s", Category.class)).thenReturn(null);

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
        when(mockEntityManager.createNativeQuery("s", Category.class)).thenReturn(null);

        // Run the test
        final List<Category> result = categoryRepositoryImplUnderTest.queryCategory(params, "sqlStr");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
