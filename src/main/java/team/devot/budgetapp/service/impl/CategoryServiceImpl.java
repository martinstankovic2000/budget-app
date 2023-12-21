package team.devot.budgetapp.service.impl;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.devot.budgetapp.exception.CustomException;
import team.devot.budgetapp.mapper.impl.CategoryMapper;
import team.devot.budgetapp.model.dto.CategoryDTO;
import team.devot.budgetapp.repository.CategoryRepository;
import team.devot.budgetapp.service.CategoryService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CategoryServiceImpl is an implementation of the CategoryService interface.
 * It provides business logic for managing categories in the application.
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    /**
     * Retrieves a category by its unique identifier.
     *
     * @param id The unique identifier of the category to be retrieved.
     * @return The CategoryDTO representing the found category.
     * @throws EntityNotFoundException If the category with the given ID is not found.
     */
    @Override
    public CategoryDTO getCategory(Long id) {
        return mapper.mapTo(categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with ID " + id + " was not found!")));
    }

    /**
     * Retrieves all categories ordered by their unique identifiers.
     *
     * @return A list of CategoryDTOs representing all categories.
     */
    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAllByOrderByid().stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    /**
     * Creates a new category.
     *
     * @param category The CategoryDTO containing information for the new category.
     * @throws CustomException If a category with the same name already exists.
     */
    @Override
    public void createCategory(CategoryDTO category) {
        if (categoryRepository.findByName(category.getName()).isPresent())
            throw new CustomException("Category with name : " + category.getName() + " already exists!");
        categoryRepository.save(mapper.mapFrom(category));
    }

    /**
     * Updates an existing category.
     *
     * @param category The CategoryDTO containing updated information for the category.
     * @return The updated CategoryDTO.
     * @throws CustomException If the category ID is null or if a category with the same name already exists.
     * @throws EntityNotFoundException If the category with the given ID is not found.
     */
    @Override
    public CategoryDTO updateCategory(CategoryDTO category) {
        if (category.getId() == null)
            throw new CustomException("Category ID cannot be null!");
        getCategory(category.getId());
        if (categoryRepository.findByName(category.getName()).isPresent())
            throw new CustomException("Category with name : " + category.getName() + " already exists!");
        return mapper.mapTo(categoryRepository.save(mapper.mapFrom(category)));
    }

    /**
     * Deletes a category by its unique identifier.
     *
     * @param id The unique identifier of the category to be deleted.
     * @throws EntityNotFoundException If the category with the given ID is not found.
     */
    @Override
    public void deleteCategory(Long id) {
        getCategory(id);
        categoryRepository.deleteById(id);
    }
}
