package team.devot.budgetapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Category is an entity representing a category in the application.
 * It is used to persist category information in the database.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Category {

    /**
     * The unique identifier of the category.
     */
    @Id
    @Column(name = "CATEGORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the category, which is unique.
     */
    @Column(name = "CATEGORY_NAME", unique = true)
    private String name;
}
