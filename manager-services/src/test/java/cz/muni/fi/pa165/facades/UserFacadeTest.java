/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.enums.UserRole;
import cz.muni.fi.pa165.facade.UserFacade;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Šimon Baláž
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class UserFacadeTest extends AbstractTestNGSpringContextTests  {
    
    @Autowired
    private UserFacade userFacade;
    
    private UserDTO testUser;    

    @BeforeMethod
    public void setup() {      
        testUser = new UserDTO();
        testUser.setName("Tester");
        testUser.setEmail("mail@test.com");
        testUser.setUserRole(UserRole.Member);  
    }
        
    @Test
    public void testRegister() {         
        userFacade.register(testUser, "password");
        testUser = userFacade.findUserByEmail("mail@test.com");
        Assert.assertTrue(0 != testUser.getId());    
        Assert.assertFalse(testUser.getPasswordHash().isEmpty());   
    }
    
    @Test
    public void testFindByEmail() {         
        userFacade.register(testUser, "password");
        UserDTO retrievedUser = userFacade.findUserByEmail("mail@test.com");
        Assert.assertNotNull(retrievedUser);
        Assert.assertEquals(retrievedUser.getName(), testUser.getName());
    }
    
    @Test
    public void testFindAllUsers() {        
        userFacade.register(testUser, "password");
        List<UserDTO> retrievedUsers = userFacade.findAllUsers();
        Assert.assertEquals(retrievedUsers.size(), 1);
        Assert.assertEquals(retrievedUsers.get(0).getEmail(), testUser.getEmail());
    }
    
}
