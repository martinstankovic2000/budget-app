package team.devot.budgetapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import team.devot.budgetapp.model.Category;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testFindByName() {
        Category category = new Category();
        category.setName("Test Category");
        categoryRepository.save(category);

        Optional<Category> foundCategory = categoryRepository.findByName("Test Category");

        assertTrue(foundCategory.isPresent());
        assertEquals("Test Category", foundCategory.get().getName());
    }

    @Test
    void testFindAllByOrderByid() {
        Category category1 = new Category();
        category1.setName("Category 1");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Category 2");
        categoryRepository.save(category2);

        List<Category> categories = categoryRepository.findAllByOrderByid();

        assertEquals(7, categories.size()); //5 already created in data.sql
        assertEquals("Category 1", categories.get(5).getName());
        assertEquals("Category 2", categories.get(6).getName());
    }
}
