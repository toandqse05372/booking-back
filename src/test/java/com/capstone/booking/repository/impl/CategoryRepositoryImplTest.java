package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CategoryConverter;
import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.dto.CategoryDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CategoryRepositoryImplTest {

    @Mock
    private EntityManager mockEntityManager;

    private CategoryRepositoryImpl categoryRepositoryImplUnderTest;

    @Mock
    Query query;

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

        BigInteger counter = BigInteger.valueOf(1);
        when(mockEntityManager.createNativeQuery("select count(pt.id) from t_category pt  where pt.type_name like :cname ")).thenReturn(query);
        when(query.setParameter("cname","")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(counter);
        when(mockEntityManager.createNativeQuery("select pt.* from t_category pt  where pt.type_name like :cname  limit :from, :limit", Category.class)).thenReturn(query);
        when(query.setParameter("from",1)).thenReturn(query);
        when(query.setParameter("limit",1)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList());
        // Run the test
        final Output result = categoryRepositoryImplUnderTest.findByMulParam("typeName", 1L, 1L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testConvertList() {
        // Setup
        Category category = new Category("typeName", "typeKey", "description");
        final List<Category> categories = Arrays.asList(category);
        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("categoryName");
        categoryDTO.setTypeKey("typeKey");
        final List<CategoryDTO> expectedResult = Arrays.asList(categoryDTO);
        when(categoryRepositoryImplUnderTest.categoryConverter.toDTO(category)).thenReturn(categoryDTO);

        // Run the test
        final List<CategoryDTO> result = categoryRepositoryImplUnderTest.convertList(categories);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testQueryCategory() {
        // Setup
        final Map<String, Object> params = new HashMap<>();
        final List<Category> expectedResult = Arrays.asList(new Category("typeName", "typeKey", "description"));
        when(mockEntityManager.createNativeQuery("select * from t_category", Category.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedResult);
        // Run the test
        final List<Category> result = categoryRepositoryImplUnderTest.queryCategory(params, "select * from t_category");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testCountCategory() {
        // Setup
        final Map<String, Object> params = new HashMap<>();
        BigInteger expectedResult = BigInteger.valueOf(0);
        when(mockEntityManager.createNativeQuery("select count(pt.id) from t_category pt")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(expectedResult);
        // Run the test
        final int result = categoryRepositoryImplUnderTest.countCategory(params, "select count(pt.id) from t_category pt");

        // Verify the results
        assertThat(result).isEqualTo(0);
    }
}
