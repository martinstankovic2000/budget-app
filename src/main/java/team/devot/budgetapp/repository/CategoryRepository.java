package team.devot.budgetapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team.devot.budgetapp.model.Category;

import java.util.List;
import java.util.Optional;


/**
 * CategoryRepository is a Spring Data JPA repository for managing Category entities.
 * It provides methods for CRUD operations and custom queries related to categories.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Finds a category by its name.
     *
     * @param name The name of the category to be found.
     * @return An Optional containing the found category, or empty if not found.
     */
    Optional<Category> findByName(String name);

    /**
     * Retrieves all categories ordered by their unique identifiers.
     *
     * @return A list of categories ordered by id.
     */
    @Query("SELECT c FROM Category c ORDER BY c.id")
    List<Category> findAllByOrderByid();
}
