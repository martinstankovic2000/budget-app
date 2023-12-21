package team.devot.budgetapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import team.devot.budgetapp.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("testUser");

        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    void testExistsByUsername() {
        User user = new User();
        user.setUsername("existingUser");
        user.setEmail("existing@example.com");
        userRepository.save(user);

        boolean exists = userRepository.existsByUsername("existingUser");

        assertTrue(exists);
    }

    @Test
    void testNotExistsByUsername() {
        boolean exists = userRepository.existsByUsername("nonexistentUser");

        assertFalse(exists);
    }

    @Test
    void testExistsByEmail() {
        User user = new User();
        user.setUsername("existingUser");
        user.setEmail("existing@example.com");
        userRepository.save(user);

        boolean exists = userRepository.existsByEmail("existing@example.com");

        assertTrue(exists);
    }

    @Test
    void testNotExistsByEmail() {
        boolean exists = userRepository.existsByEmail("nonexistent@example.com");

        assertFalse(exists);
    }
}
