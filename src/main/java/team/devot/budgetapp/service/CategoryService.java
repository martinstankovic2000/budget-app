package team.devot.budgetapp.service;

import team.devot.budgetapp.model.dto.CategoryDTO;

import java.util.List;

/**
 * CategoryService is an interface defining the contract for managing category-related operations.
 */
public interface CategoryService {

    /**
     * Retrieves a category by its unique identifier.
     *
     * @param id The unique identifier of the category to be retrieved.
     * @return The CategoryDTO representing the found category.
     */
    CategoryDTO getCategory(Long id);

    /**
     * Retrieves all categories.
     *
     * @return A list of CategoryDTO representing all categories.
     */
    List<CategoryDTO> getAllCategories();

    /**
     * Creates a new category.
     *
     * @param category The CategoryDTO containing information for the new category.
     */
    void createCategory(CategoryDTO category);

    /**
     * Updates an existing category.
     *
     * @param category The CategoryDTO containing updated information for the category.
     * @return The updated CategoryDTO.
     */
    CategoryDTO updateCategory(CategoryDTO category);

    /**
     * Deletes a category by its unique identifier.
     *
     * @param id The unique identifier of the category to be deleted.
     */
    void deleteCategory(Long id);
}
