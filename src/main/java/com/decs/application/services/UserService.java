package com.decs.application.services;

import com.decs.application.data.user.User;
import com.decs.application.data.user.UserRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * <b>User Service Class</b>
 * <p>
 *     This class represents a management service for the system's users.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
@Service
public class UserService {

    private final UserRepository repository;

    /**
     * Class Constructor
     * @param repository System's user repository
     */
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Find and retrieve a User object by its identification number
     * @param id User identification number
     * @return Respective user object
     */
    public Optional<User> get(Long id) {
        return repository.findById(id);
    }

    /**
     * Add a user to the repository
     * @param entity User to be added
     * @return Added user
     */
    public User update(User entity) {
        return repository.save(entity);
    }

    /**
     * Delete a user by its identification number
     * @param id User identification number
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<User> list(Pageable pageable, Specification<User> filter) {
        return repository.findAll(filter, pageable);
    }

    /**
     * @return Number of users in the user repository
     */
    public int count() {
        return (int) repository.count();
    }

}
