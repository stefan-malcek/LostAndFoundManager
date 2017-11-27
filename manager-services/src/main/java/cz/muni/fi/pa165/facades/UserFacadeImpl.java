/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.UserUpdateDTO;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.services.MappingService;
import cz.muni.fi.pa165.services.UserService;
import exceptions.LostAndFoundManagerDataAccessException;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Šimon Baláž
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {    
    @Inject
    private UserService userService;
     
    @Inject
    private MappingService mappingService;

    @Override
    public UserDTO findUserById(long id) {
        try {
            User user = userService.findUserById(id);
                    
            if(user == null) {
                return null;
            }        
            return mappingService.mapTo(user, UserDTO.class);
        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("Cannot find user by id",e);
        }
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        try {
            User user = userService.findUserByEmail(email);        
        
            if(user == null) {
                return null;
            }         
            return mappingService.mapTo(user, UserDTO.class);
        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("Cannot find user by email",e);
        }
    }

    @Override
    public List<UserDTO> findAllUsers() {
        try {
            List<User> users = userService.findAllUsers();
            return mappingService.mapTo(users, UserDTO.class);
        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("Cannot find all users",e);
        }        
    }
    
    @Override
    public boolean isAdministrator(UserDTO user) {
        try {
            User mappedUser = mappingService.mapTo(user, User.class);        
            return userService.isAdministrator(mappedUser);        
        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("Cannot check authorization",e);
        } 
    }

    @Override
    public void register(UserDTO user, String password) {
        try {
            User mappedUser = mappingService.mapTo(user, User.class);      
            userService.register(mappedUser, password);
        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("Cannot register user",e);
        } 
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO user) {
        try {
            User retrievedUser = userService.findUserById(user.getId());
            return userService.authenticate(retrievedUser, user.getPassword());
        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("Cannot authenticate user",e);
        } 
    }  

    @Override
    public void update(UserUpdateDTO user) {
        try {
            User retrievedUser = userService.findUserById(user.getId());
            userService.update(retrievedUser, user.getName());
        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("Cannot update user",e);
        } 
    }

    @Override
    public void changePassword(UserAuthenticateDTO user, String newPassword) {
        try {
            User retrievedUser = userService.findUserById(user.getId());
            userService.changePassword(retrievedUser, user.getPassword(), newPassword);
        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("Cannot change password",e);
        }          
    }

    @Override
    public void delete(long id) {
        try {
            User user = userService.findUserById(id);
            userService.delete(user);
        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("Cannot remove user",e);
        } 
    }
    
}
