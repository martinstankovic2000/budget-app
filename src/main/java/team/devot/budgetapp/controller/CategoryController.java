package team.devot.budgetapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.devot.budgetapp.model.dto.CategoryDTO;
import team.devot.budgetapp.service.CategoryService;

import java.util.List;

/**
 * Controller class for handling HTTP requests related to categories.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Retrieves a category by its identifier.
     *
     * @param id The unique identifier of the category.
     * @return The CategoryDTO representing the retrieved category.
     */
    @GetMapping("/category/{id}")
    public CategoryDTO getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    /**
     * Retrieves all categories.
     *
     * @return A List of CategoryDTOs representing all categories.
     */
    @GetMapping("/category")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * Creates a new category.
     *
     * @param category The CategoryDTO representing the new category.
     */
    @PostMapping("/category")
    public void createCategory(@RequestBody CategoryDTO category) {
        categoryService.createCategory(category);
    }

    /**
     * Updates an existing category.
     *
     * @param category The CategoryDTO representing the updated category.
     * @return The CategoryDTO representing the updated category.
     */
    @PutMapping("/category")
    public CategoryDTO updateCategory(@RequestBody CategoryDTO category) {
        return categoryService.updateCategory(category);
    }

    /**
     * Deletes a category by its identifier.
     *
     * @param id The unique identifier of the category to be deleted.
     */
    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
