package team.devot.budgetapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team.devot.budgetapp.model.dto.CategoryDTO;
import team.devot.budgetapp.service.CategoryService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    void testGetCategory() throws Exception {
        long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("test");

        when(categoryService.getCategory(categoryId)).thenReturn(categoryDTO);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/category/{id}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"));

        verify(categoryService, times(1)).getCategory(categoryId);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void testGetAllCategories() throws Exception {
        List<CategoryDTO> categories = Arrays.asList(new CategoryDTO(), new CategoryDTO());

        when(categoryService.getAllCategories()).thenReturn(categories);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/category"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(categories.size()));

        verify(categoryService, times(1)).getAllCategories();
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void testCreateCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(categoryDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(categoryService, times(1)).createCategory(categoryDTO);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void testUpdateCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("test");

        when(categoryService.updateCategory(categoryDTO)).thenReturn(categoryDTO);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(categoryDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"));

        verify(categoryService, times(1)).updateCategory(categoryDTO);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void testDeleteCategory() throws Exception {
        long categoryId = 1L;

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/category/{id}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(categoryService, times(1)).deleteCategory(categoryId);
        verifyNoMoreInteractions(categoryService);
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
