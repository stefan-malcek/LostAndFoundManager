/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.UserDTO;
import java.util.List;

/**
 *
 * @author Šimon Baláž
 */
public interface UserFacade {    
    UserDTO findUserById(Long id);    
    UserDTO findUserByEmail(String email);
    List<UserDTO> findAllUsers();
    boolean isAdministrator(UserDTO user);
}
