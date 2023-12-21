package team.devot.budgetapp.service;

import team.devot.budgetapp.model.dto.UserDTO;

/**
 * UserService is an interface defining the contract for user-related operations, including registration, login and logout.
 */
public interface UserService {

    /**
     * Registers a new user.
     *
     * @param userDTO The UserDTO containing information for the new user.
     */
    void registerUser(UserDTO userDTO);

    /**
     * Logs in a user.
     *
     * @param userDTO The UserDTO containing the username and password for login.
     */
    void login(UserDTO userDTO);

    /**
     * Logs out a user.
     *
     * @param userDTO The UserDTO containing the username for logout.
     */
    void logout(UserDTO userDTO);
}
