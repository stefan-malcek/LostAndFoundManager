/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.enums.UserRole;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Šimon Baláž
 */
@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;    

    @Override
    public User findUserById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }
    
    public boolean isAdministrator(User user) {
        User retrievedUser = findUserById(user.getId());
        return retrievedUser.getUserRole().equals(UserRole.Administrator);
    }
    
}
