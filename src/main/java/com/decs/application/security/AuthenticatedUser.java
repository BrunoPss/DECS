package com.decs.application.security;

import com.decs.application.data.user.User;
import com.decs.application.data.user.UserRepository;
import com.vaadin.flow.spring.security.AuthenticationContext;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>Authenticated User Class</b>
 * <p>
 *     This class represents a authenticated user in the system.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
@Component
public class AuthenticatedUser {

    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;

    /**
     * Authenticated User class constructor
     * @param authenticationContext Authentication context object
     * @param userRepository System's user repository
     */
    public AuthenticatedUser(AuthenticationContext authenticationContext, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.authenticationContext = authenticationContext;
    }

    /**
     * Gets the authenticated user
     * @return the authenticated User object
     */
    @Transactional
    public Optional<User> get() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .map(userDetails -> userRepository.findByUsername(userDetails.getUsername()));
    }

    /**
     * Logs out the authenticated user
     */
    public void logout() {
        authenticationContext.logout();
    }

}
