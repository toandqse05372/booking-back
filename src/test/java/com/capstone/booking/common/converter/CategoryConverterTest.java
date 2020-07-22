package com.capstone.booking.common.converter;

import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.dto.CategoryDTO;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryConverterTest {

    private CategoryConverter categoryConverterUnderTest;

    @Before
    public void setUp() {
        categoryConverterUnderTest = new CategoryConverter();
    }

    @Test
    public void testToDTO() {
        // Setup
        final Category category = new Category("typeName", "typeKey");
        final CategoryDTO expectedResult = new CategoryDTO();
        expectedResult.setCategoryName("categoryName");
        expectedResult.setTypeKey("typeKey");
        expectedResult.setIconLink("iconLink");

        // Run the test
        final CategoryDTO result = categoryConverterUnderTest.toDTO(category);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testToCategory() {
        // Setup
        final CategoryDTO dto = new CategoryDTO();
        dto.setCategoryName("categoryName");
        dto.setTypeKey("typeKey");
        dto.setIconLink("iconLink");

        final Category expectedResult = new Category("typeName", "typeKey");

        // Run the test
        final Category result = categoryConverterUnderTest.toCategory(dto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testToCategory1() {
        // Setup
        final CategoryDTO dto = new CategoryDTO();
        dto.setCategoryName("categoryName");
        dto.setTypeKey("typeKey");
        dto.setIconLink("iconLink");

        final Category category = new Category("typeName", "typeKey");
        final Category expectedResult = new Category("typeName", "typeKey");

        // Run the test
        final Category result = categoryConverterUnderTest.toCategory(dto, category);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
