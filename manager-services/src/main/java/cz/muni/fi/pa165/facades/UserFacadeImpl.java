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
        User user = userService.findUserById(id);
        
        if(user == null) {
            return null;
        }        
        return mappingService.mapTo(user, UserDTO.class); 
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userService.findUserByEmail(email);
        
        if(user == null) {
            return null;
        }         
        return mappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userService.findAllUsers();
        return mappingService.mapTo(users, UserDTO.class);
    }
    
    @Override
    public boolean isAdministrator(UserDTO user) {    
        User mappedUser = mappingService.mapTo(user, User.class);
        return userService.isAdministrator(mappedUser);        
    }

    @Override
    public void register(UserDTO user, String password) {
        User mappedUser = mappingService.mapTo(user, User.class);
        userService.register(mappedUser, password);
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO user) {
        User retrievedUser = userService.findUserById(user.getId());
        return userService.authenticate(retrievedUser, user.getPassword());
    }  

    @Override
    public void update(UserUpdateDTO user) {
        User retrievedUser = userService.findUserById(user.getId());
        userService.update(retrievedUser, user.getName());
    }

    @Override
    public void changePassword(UserAuthenticateDTO user, String newPassword) {
        User retrievedUser = userService.findUserById(user.getId());
        userService.changePassword(retrievedUser, user.getPassword(), newPassword);
         
    }

    @Override
    public void delete(long id) {
        User user = userService.findUserById(id);
        userService.delete(user);
    }
    
}
