package com.decs.application.data.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * <b>User Repository Interface</b>
 * <p>
 *     This interface defines the user repository methods.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    /**
     * Search and retrieve a system user by its username
     * @param username name of the user to be found
     * @return User object
     */
    User findByUsername(String username);
}
