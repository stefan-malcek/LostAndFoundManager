/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.enums.UserRole;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Šimon Baláž
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private UserDao userDao;
    
    @Autowired
    @InjectMocks
    private UserService userService;    
    
    @BeforeClass
    public void setup() throws ServiceException
    {
        MockitoAnnotations.initMocks(this);
    }
    
    private User testUser;
    
    @BeforeMethod
    public void createUser(){
        testUser = new User();
        testUser.setName("Tester");
        testUser.setEmail("mail@test.com");
        testUser.setUserRole(UserRole.MEMBER);
    }    
    
    @Test
    public void testFindById() {
        when(userDao.findById(0)).thenReturn(testUser);
        
        User retrievedUser = userService.findUserById(0);
        Assert.assertEquals(retrievedUser, testUser); 
    }
    
    @Test
    public void testFindByEmail() {
        when(userDao.findUserByEmail("mail@test.com")).thenReturn(testUser);
        
        User retrievedUser = userService.findUserByEmail("mail@test.com");
        Assert.assertEquals(retrievedUser, testUser);
    }   
    
    @Test
    public void testFindAllUsers() {
        List<User> users = new ArrayList();
        users.add(testUser);
        
        when(userDao.findAll()).thenReturn(users);
        
        List<User> retrievedUsers = userService.findAllUsers();
        Assert.assertEquals(1, retrievedUsers.size());
        Assert.assertEquals(retrievedUsers.get(0), testUser);
    } 
    
    @Test
    public void testIsAdministrator() {
        when(userDao.findById(0)).thenReturn(testUser);
        boolean admin = userService.isAdministrator(testUser);
        Assert.assertFalse(admin);
    }
    
    @Test
    public void testRegister() {
        userService.register(testUser, "password");
        Assert.assertFalse(testUser.getPasswordHash().isEmpty());
        verify(userDao, atLeastOnce()).create(testUser);
    }  
    
    @Test
    public void testAuthenticate() {        
        userService.register(testUser, "password");       
        boolean authenticated = userService.authenticate(testUser, "password");
        Assert.assertTrue(authenticated);
    }
    
    @Test
    public void testAuthenticateIncorrectPassword() {        
        userService.register(testUser, "password");       
        boolean authenticated = userService.authenticate(testUser, "12345");
        Assert.assertFalse(authenticated);
    }
    
    @Test
    public void testUpdate() {       
        String newName = "Newneman";
        userService.update(testUser, newName);
        Assert.assertEquals(testUser.getName(), newName);
    }
    
    @Test
    public void testChangePassword() {
        userService.register(testUser, "password");     
        String originalHash = testUser.getPasswordHash();
        userService.changePassword(testUser, "password", "12345");
        Assert.assertFalse(originalHash.equals(testUser.getPasswordHash()));
    }   
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangePasswordNonAuthenticated() {
        userService.register(testUser, "password");          
        userService.changePassword(testUser, "123", "12345");        
    }   
    
    @Test
    public void testDelete() {       
        userService.delete(testUser);        
        verify(userDao, atLeastOnce()).delete(testUser);        
    }
    
}
