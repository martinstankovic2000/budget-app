package team.devot.budgetapp.mapper.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import team.devot.budgetapp.model.Category;
import team.devot.budgetapp.model.dto.CategoryDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CategoryMapperTest {

    @InjectMocks
    private CategoryMapper categoryMapper;

    @Test
    void testMapTo() {
        Category category = Category.builder()
                .id(1L)
                .name("Test Category")
                .build();

        CategoryDTO categoryDTO = categoryMapper.mapTo(category);

        assertEquals(1L, categoryDTO.getId());
        assertEquals("Test Category", categoryDTO.getName());
    }

    @Test
    void testMapFrom() {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(1L)
                .name("Test Category")
                .build();

        Category category = categoryMapper.mapFrom(categoryDTO);

        assertEquals(1L, category.getId());
        assertEquals("Test Category", category.getName());
    }
}
