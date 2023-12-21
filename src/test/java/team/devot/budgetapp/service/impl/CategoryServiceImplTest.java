package team.devot.budgetapp.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.devot.budgetapp.exception.CustomException;
import team.devot.budgetapp.mapper.impl.CategoryMapper;
import team.devot.budgetapp.model.Category;
import team.devot.budgetapp.model.dto.CategoryDTO;
import team.devot.budgetapp.repository.CategoryRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void testGetCategory() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.mapTo(category)).thenReturn(new CategoryDTO(categoryId, "TestCategory"));

        CategoryDTO result = categoryService.getCategory(categoryId);

        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testGetCategoryNotFound() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.getCategory(categoryId));
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testGetAllCategories() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");

        when(categoryRepository.findAllByOrderByid()).thenReturn(Arrays.asList(category1, category2));
        when(categoryMapper.mapTo(category1)).thenReturn(new CategoryDTO(1L, "Category 1"));
        when(categoryMapper.mapTo(category2)).thenReturn(new CategoryDTO(2L, "Category 2"));

        List<CategoryDTO> result = categoryService.getAllCategories();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Category 1", result.get(0).getName());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Category 2", result.get(1).getName());
        verify(categoryRepository, times(1)).findAllByOrderByid();
    }

    @Test
    void testCreateCategory() {
        CategoryDTO categoryDTO = new CategoryDTO(null, "New Category");
        when(categoryRepository.findByName("New Category")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> categoryService.createCategory(categoryDTO));

        verify(categoryRepository, times(1)).findByName("New Category");
        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    void testCreateCategoryAlreadyExists() {
        CategoryDTO categoryDTO = new CategoryDTO(null, "Existing Category");
        when(categoryRepository.findByName("Existing Category")).thenReturn(Optional.of(new Category()));

        assertThrows(CustomException.class, () -> categoryService.createCategory(categoryDTO));
        verify(categoryRepository, times(1)).findByName("Existing Category");
        verify(categoryRepository, times(0)).save(any());
    }

    @Test
    void testUpdateCategory() {
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Updated Category");
        Category category = new Category(1L, "Test");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.findByName("Updated Category")).thenReturn(Optional.empty());
        when(categoryRepository.save(any())).thenReturn(new Category(1L, "Updated Category"));
        when(categoryMapper.mapFrom(any())).thenReturn(new Category());
        when(categoryMapper.mapTo(any())).thenReturn(new CategoryDTO());
        CategoryDTO result = categoryService.updateCategory(categoryDTO);

        assertNotNull(result);
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).findByName("Updated Category");
        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    void testUpdateCategoryNullId() {
        CategoryDTO categoryDTO = new CategoryDTO(null, "Updated Category");

        assertThrows(CustomException.class, () -> categoryService.updateCategory(categoryDTO));
        verify(categoryRepository, times(0)).findById(any());
        verify(categoryRepository, times(0)).findByName(any());
        verify(categoryRepository, times(0)).save(any());
    }

    @Test
    void testUpdateCategoryAlreadyExists() {
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Existing Category");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category()));
        when(categoryRepository.findByName("Existing Category")).thenReturn(Optional.of(new Category()));

        assertThrows(CustomException.class, () -> categoryService.updateCategory(categoryDTO));
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).findByName("Existing Category");
        verify(categoryRepository, times(0)).save(any());
    }

    @Test
    void testDeleteCategory() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(new Category()));

        assertDoesNotThrow(() -> categoryService.deleteCategory(categoryId));

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void testDeleteCategoryNotFound() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteCategory(categoryId));
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(0)).deleteById(categoryId);
    }
}
