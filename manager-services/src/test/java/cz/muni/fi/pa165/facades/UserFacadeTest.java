/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.UserUpdateDTO;
import cz.muni.fi.pa165.enums.UserRole;
import cz.muni.fi.pa165.facade.UserFacade;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Šimon Baláž
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserFacadeTest extends AbstractTestNGSpringContextTests  {
    
    @Autowired
    private UserFacade userFacade;
    
    private UserDTO testUser;    

    @BeforeMethod
    public void setup() {      
        testUser = new UserDTO();
        testUser.setName("Tester");
        testUser.setEmail("mail@test.com");
        testUser.setUserRole(UserRole.MEMBER);
        
        userFacade.register(testUser, "password");
        testUser = userFacade.findUserByEmail("mail@test.com");
    }
        
    @Test
    public void testRegister() {             
        Assert.assertTrue(0 != testUser.getId());    
        Assert.assertFalse(testUser.getPasswordHash().isEmpty());   
    }
    
    @Test
    public void testFindById() {
        UserDTO retrievedUser = userFacade.findUserById(testUser.getId());
        Assert.assertNotNull(retrievedUser);
        Assert.assertEquals(retrievedUser, testUser);
    }
    
    @Test
    public void testFindByEmail() {
        Assert.assertNotNull(testUser);
        Assert.assertEquals(testUser.getName(), "Tester");
    }
    
    @Test
    public void testFindAllUsers() {
        List<UserDTO> retrievedUsers = userFacade.findAllUsers();
        Assert.assertEquals(retrievedUsers.size(), 1);
        Assert.assertEquals(retrievedUsers.get(0).getEmail(), testUser.getEmail());
    }    
    
    @Test
    public void testIsAdministrator() {        
        boolean administrator = userFacade.isAdministrator(testUser);
        Assert.assertFalse(administrator);
    }  
    
    @Test
    public void testAuthenticate() {        
        UserAuthenticateDTO authUser = new UserAuthenticateDTO();
        authUser.setEmail(testUser.getEmail());
        authUser.setPassword("password");
        
        boolean authenticated = userFacade.authenticate(authUser);
        Assert.assertTrue(authenticated);
    }
    
    @Test
    public void testUpdate() {
        UserUpdateDTO updatedUser = new UserUpdateDTO();
        updatedUser.setId(testUser.getId());
        updatedUser.setName("Newneman");
        
        userFacade.update(updatedUser);
        testUser = userFacade.findUserByEmail("mail@test.com");
        Assert.assertEquals(testUser.getName(), updatedUser.getName());        
    }
    
    @Test
    public void testChangePassword() {
        UserAuthenticateDTO authUser = new UserAuthenticateDTO();
        authUser.setEmail(testUser.getEmail());
        authUser.setPassword("password");
        
        userFacade.changePassword(authUser, "12345");
        UserDTO retrievedUser = userFacade.findUserByEmail("mail@test.com");
        Assert.assertFalse(testUser.getPasswordHash().equals(retrievedUser.getPasswordHash()));
    }
    
    @Test
    public void testDelete() {
        userFacade.delete(testUser.getId());
        List<UserDTO> retrievedUsers = userFacade.findAllUsers();
        Assert.assertEquals(retrievedUsers.size(), 0);
    }
    
    
}
