package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CategoryConverter;
import com.capstone.booking.common.converter.CityConverter;
import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.City;
import com.capstone.booking.entity.dto.CategoryDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CategoryRepositoryImplTest {

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private Query query;

    private CategoryRepositoryImpl categoryRepositoryImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        categoryRepositoryImplUnderTest = new CategoryRepositoryImpl(mockEntityManager);
        categoryRepositoryImplUnderTest.categoryConverter = mock(CategoryConverter.class);
    }

    @Test
    public void testFindByMulParam() {
        // Setup
        final Output expectedResult = new Output();
        expectedResult.setPage(1);
        expectedResult.setTotalPage(1);
        expectedResult.setListResult(Arrays.asList());
        expectedResult.setTotalItems(1);

        final List<Category> categories = new ArrayList<>();
        final Category category = new Category();
        category.setTypeName("categoryName");
        category.setTypeKey("typeKey");
        category.setIconLink("iconLink");
        categories.add(category);

        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("categoryName");
        categoryDTO.setTypeKey("typeKey");
        categoryDTO.setIconLink("iconLink");
        List<CategoryDTO> categoryDTOS = Arrays.asList(categoryDTO);
        expectedResult.setListResult(categoryDTOS);

        when(mockEntityManager.createNativeQuery("select pt.* from t_category pt  where pt.type_name like :cname ", Category.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(categories);
        when(categoryRepositoryImplUnderTest.categoryConverter.toDTO(category)).thenReturn(categoryDTO);

        // Run the test
        final Output result = categoryRepositoryImplUnderTest.findByMulParam("typeName", 1L, 1L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testConvertList() {
        // Setup
        final List<Category> categories = new ArrayList<>();
        final Category category = new Category();
        category.setTypeName("categoryName");
        category.setTypeKey("typeKey");
        category.setIconLink("iconLink");
        categories.add(category);
        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("categoryName");
        categoryDTO.setTypeKey("typeKey");
        categoryDTO.setIconLink("iconLink");
        when(categoryRepositoryImplUnderTest.categoryConverter.toDTO(category)).thenReturn(categoryDTO);
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
        when(mockEntityManager.createNativeQuery("select * from t_category", Category.class)).thenReturn(query);

        // Run the test
        final List<Category> result = categoryRepositoryImplUnderTest.queryCategory(params, "select * from t_category");

        // Verify the results
        Assert.assertNotNull(result);
    }
}
