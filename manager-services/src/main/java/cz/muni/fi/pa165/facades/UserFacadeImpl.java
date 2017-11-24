/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.services.BeanMappingService;
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
    private BeanMappingService beanMappingService;

    @Override
    public UserDTO findUserById(Long id) {
        User user = userService.findUserById(id);
        
        if(user == null) {
            return null;
        }        
        return beanMappingService.mapTo(user, UserDTO.class); 
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userService.findUserByEmail(email);
        
        if(user == null) {
            return null;
        }         
        return beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userService.findAllUsers();
        return beanMappingService.mapTo(users, UserDTO.class);
    }
    
    @Override
    public boolean isAdministrator(UserDTO user) {    
        User mappedUser = beanMappingService.mapTo(user, User.class);
        return userService.isAdministrator(mappedUser);        
    }
    
}
