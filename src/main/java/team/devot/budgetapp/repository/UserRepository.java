package team.devot.budgetapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.devot.budgetapp.model.User;

import java.util.Optional;

/**
 * UserRepository is a Spring Data JPA repository for managing User entities.
 * It provides methods for CRUD operations and custom queries related to users.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by username.
     *
     * @param username The username of the user to be found.
     * @return An Optional containing the found user, or empty if not found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user with the given username exists.
     *
     * @param username The username to check for existence.
     * @return true if a user with the given username exists, false otherwise.
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user with the given email exists.
     *
     * @param email The email to check for existence.
     * @return true if a user with the given email exists, false otherwise.
     */
    boolean existsByEmail(String email);
}
