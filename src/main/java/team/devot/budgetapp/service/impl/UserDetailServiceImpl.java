package team.devot.budgetapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.devot.budgetapp.model.User;
import team.devot.budgetapp.repository.UserRepository;

/**
 * UserDetailServiceImpl is an implementation of the UserDetailsService interface.
 * It provides a custom implementation for loading user details during authentication.
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads user details by username.
     *
     * @param username The username for which user details should be loaded.
     * @return A UserDetails object representing the loaded user details.
     * @throws UsernameNotFoundException If the user is not found in the repository.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
