package org.atilla.cytraining.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import org.atilla.cytraining.user.User;
import org.atilla.cytraining.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * A service dedicated to authentication.
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    /**
     * Check if a user with the given username exists.
     *
     * @param username the name of the user to check
     * @return `true` if a user with this name exists
     */
    public boolean doesUserExist(String username) {
        return !em.createNativeQuery("SELECT * FROM users WHERE username = :uname", User.class)
                .setParameter("uname", username)
                .getResultList()
                .isEmpty();
    }

    /**
     * Try to log in the user using its name and password.
     *
     * @param username the name of the user
     * @param password the password of the user
     * @return the User corresponding to the given credentials or an empty Optional
     *         if no user is matched
     */
    public Optional<User> login(String username, String password) {
        try {
            User user = (User) em
                    .createNativeQuery(
                            "SELECT * FROM users WHERE username = :uname",
                            User.class)
                    .setParameter("uname", username)
                    .getSingleResult();

            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Register a new user.
     *
     * @param username the name of the user
     * @param password the password of the user
     * @return a new user with the given credentials
     */
    public User register(String username, String password) {
        if (password != "" && !doesUserExist(username)) {
            return userRepository
                    .save(new User(username, password, false, false, false, Set.of(), Set.of()));
        } else {
            return null;
        }
    }
}
