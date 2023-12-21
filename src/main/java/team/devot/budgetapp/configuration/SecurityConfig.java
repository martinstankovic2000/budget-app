package team.devot.budgetapp.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

/**
 * Security configuration class for handling authentication and authorization in the application.
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;


    /**
     * Provides a BCryptPasswordEncoder bean for password encoding.
     *
     * @return The BCryptPasswordEncoder bean.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides an AuthenticationManager bean for managing authentication.
     *
     * @return The AuthenticationManager bean.
     * @throws Exception If an error occurs while providing the authentication manager.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Configures the global AuthenticationManagerBuilder.
     *
     * @param auth The AuthenticationManagerBuilder instance.
     * @throws Exception If an error occurs while configuring the authentication manager.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Configures the HttpSecurity to define which paths are secured.
     *
     * @param http The HttpSecurity instance.
     * @throws Exception If an error occurs while configuring the security.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers( "/api/login", "/api/register", "/api/logout").permitAll()
                .anyRequest().authenticated();
    }
}