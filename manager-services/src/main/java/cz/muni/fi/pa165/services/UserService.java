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
    User findUserById(Long id);    
    User findUserByEmail(String email);
    List<User> findAllUsers();
    boolean isAdministrator(User user);   
   
}
