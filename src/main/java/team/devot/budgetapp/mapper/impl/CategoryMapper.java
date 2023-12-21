package team.devot.budgetapp.mapper.impl;

import org.springframework.stereotype.Component;
import team.devot.budgetapp.mapper.Mapper;
import team.devot.budgetapp.model.Category;
import team.devot.budgetapp.model.dto.CategoryDTO;

/**
 * CategoryMapper is a component responsible for mapping between Category entities and CategoryDTOs.
 * It implements the Mapper interface for bidirectional mapping.
 */
@Component
public class CategoryMapper implements Mapper<Category, CategoryDTO> {


    /**
     * Maps a Category entity to a CategoryDTO.
     *
     * @param category The Category entity to be mapped.
     * @return The corresponding CategoryDTO.
     */
    @Override
    public CategoryDTO mapTo(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    /**
     * Maps a CategoryDTO to a Category entity.
     *
     * @param categoryDTO The CategoryDTO to be mapped.
     * @return The corresponding Category entity.
     */
    @Override
    public Category mapFrom(CategoryDTO categoryDTO) {
        return Category.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .build();
    }
}
