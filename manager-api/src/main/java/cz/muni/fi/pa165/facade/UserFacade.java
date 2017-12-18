/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.UserUpdateDTO;
import java.util.List;

/**
 *
 * @author Šimon Baláž
 */
public interface UserFacade {    
    /**
     * Retrieves the user with given id.
     * @param id Id of the user 
     * @return {@code User} with given id 
     */
    UserDTO findUserById(long id);    
    
    /**
     * Retrieves the user with given email.
     * @param email Email of the user
     * @return {@code User} with given email
     */
    UserDTO findUserByEmail(String email);
    
    /**
     * Retrieves all users.
     * @return {@code List} of users 
     */
    List<UserDTO> findAllUsers();
    
    /**
     * Checks whether the user is administrator.
     * @param user User to check
     * @return {@code True} if the user is administrator 
     */
    boolean isAdministrator(UserDTO user);
    
    /**
     * Registers new user.
     * @param user User to register
     * @param password Password of the new user
     */
    Long register(UserDTO user, String password);
    
    /**
     * Checks if the user is authenticated.
     * @param user User to authenticate
     * @return {@code True} if the user is authenticated
     */
    boolean authenticate(UserAuthenticateDTO user);
    
    /**
     * Changes user information.
     * @param user New user information
     */
    void update(UserUpdateDTO user);
    
    /**
     * Changes password of the user.
     * @param user User to authenticate
     * @param newPassword New password
     */
    void changePassword(UserAuthenticateDTO user, String newPassword);
    
    /**
     * Removes user.
     * @param id Id of the user
     */
    void delete(long id);
}
