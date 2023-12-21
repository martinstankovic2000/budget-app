package team.devot.budgetapp.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User is an entity representing a user in the application.
 * It is used to persist user information in the database.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    /**
     * The unique identifier of the user.
     */
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user, which is unique.
     */
    @Column(name = "USER_USERNAME", unique = true)
    private String username;

    /**
     * The password of the user.
     */
    @Column(name = "USER_PASSWORD")
    private String password;

    /**
     * The email address of the user, which is unique.
     */
    @Column(name = "USER_EMAIL", unique = true)
    private String email;

    /**
     * The balance associated with the user.
     */
    @Column(name = "USER_BALANCE")
    private Double balance;
}
