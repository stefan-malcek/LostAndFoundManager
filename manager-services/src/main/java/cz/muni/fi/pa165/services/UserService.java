/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.entities.User;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Šimon Baláž
 */
@Service
public interface UserService {
    /**
     * Retrieves the user with given id.
     * @param id Id of the user 
     * @return {@code User} with given id 
     */
    User findUserById(long id);   
    
    /**
     * Retrieves the user with given email.
     * @param email Email of the user
     * @return {@code User} with given email
     */
    User findUserByEmail(String email);
    
    /**
     * Retrieves all users.
     * @return {@code List} of users 
     */
    List<User> findAllUsers();
    
     /**
     * Checks whether the user is administrator.
     * @param user User to check
     * @return {@code True} if the user is administrator 
     */
    boolean isAdministrator(User user);  
    
    /**
     * Registers new user.
     * @param user User to register
     * @param password Password of the new user
     */
    void register(User user, String password);
    
    /**
     * Checks if the user is authenticated.
     * @param user User to authenticate
     * @param password Password of the user
     * @return {@code True} if the user is authenticated
     */
    boolean authenticate(User user, String password);
    
    /**
     * Changes user information.
     * @param user User whose information is changed
     * @param newName New name
     */
    void update(User user, String newName);
    
    /**
     * Changes password of the user.
     * @param user User to authenticate
     * @param oldPassword Old password
     * @param newPassword New password
     */
    void changePassword(User user, String oldPassword, String newPassword);
    
     /**
     * Removes user.
     * @param user User to delete
     */
    void delete(User user);
   
}
