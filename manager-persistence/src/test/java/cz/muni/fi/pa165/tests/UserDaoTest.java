/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.tests;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.enums.UserRole;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import java.util.List;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
 * @author robhavalicek
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {
    
    
    @Autowired
    private UserDao userDao;
    
    private User user;
    
    private User user2;

    @BeforeMethod
        public void setup() {       
        user = new User();
        user.setName("Josef Novák");
        user.setEmail("admin@lost.com");
        user.setUserRole(UserRole.ADMINISTRATOR);
        user.setPasswordHash("1245");
        
        user2 = new User();
        user2.setName("Monika Bledá");
        user2.setEmail("user@lost.com");
        user2.setUserRole(UserRole.MEMBER);
        user2.setPasswordHash("1245");
    }
        
    @Test(expectedExceptions = ValidationException.class)   
    public void testCreateNullName_throwsException() { 
           user.setName(null);
           userDao.create(user);
    }
   
    @Test(expectedExceptions =  DataAccessException.class)   
    public void testCreateNull_throwsException() {          
           userDao.create(null);
    }
    
    @Test(expectedExceptions = DataAccessException.class) 
    public void testCreateRoleNull_throwsException() { 
               user.setUserRole(null);
               userDao.create(null);
    }
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateNullEmail_throwsException() {           
               user.setEmail(null);
               userDao.create(user);
    }
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateInvalidEmail_throwsException() {    
               user.setEmail("error");
               userDao.create(user);
    }
      
    @Test
    public void testCreate() {                        
        Assert.assertEquals(0,user.getId());
        userDao.create(user);
        Assert.assertTrue(user.getId()!=0);
    }
    
    @Test
    public void testFindById() {
                               
        Assert.assertEquals(0,user.getId());
        userDao.create(user);
         Assert.assertTrue(user.getId()!=0);
                             
        User userFromDb = userDao.findById(user.getId());               
        Assert.assertEquals(userFromDb.getId(), user.getId());                
    }
    
        @Test
    public void testFindAll() {
                               
        userDao.create(user);
        userDao.create(user2);
        
        List<User> usersFromDb = userDao.findAll();
        Assert.assertEquals(usersFromDb.size(), 2);    
        Assert.assertEquals(usersFromDb.get(0), user);    
        Assert.assertEquals(usersFromDb.get(1), user2);    
    }
    
    @Test
    public void testDelete() {       
        
        userDao.create(user);
        userDao.create(user2);
        
        List<User> usersFromDb = userDao.findAll();
        Assert.assertEquals(usersFromDb.size(), 2);        
        userDao.delete(user);    
        
        usersFromDb = userDao.findAll();
        Assert.assertEquals(usersFromDb.size(), 1);
        Assert.assertEquals(usersFromDb.get(0), user2);        

    }
}
