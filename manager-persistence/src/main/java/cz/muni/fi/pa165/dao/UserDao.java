package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.User;
import java.util.List;

/**
 * Represents a Data Access Object for User entity.
 * @author Stefan Malcek
 */
public interface UserDao {
    
    /**
     * Stores a new user in the application.
     * @param user User to create
     */
    void create(User user);
    
    /**
     * Deletes the user with given id.
     * @param user User to delete
     */
    void delete(User user);
    
    /**
     * Retrieves the user with given id.
     * @param id Id of the user
     * @return {@code User} with given id
     */
    User findById(long id);
    
    /**
     * Retrieves the user with given email.
     * @param email Email of the user
     * @return {@code User} with given id 
     * or {@code null} if was not found. 
     */
    User findUserByEmail(String email);
    
    /**
     * Retrieves all users.
     * @return {@code List} of users
     */
    List<User> findAll();
}
