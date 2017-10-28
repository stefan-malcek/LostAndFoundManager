/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.tests;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.enums.UserRole;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;
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
        List<User> users = userDao.findAll();
        for (User u : users)
            userDao.delete(u);
               
        user = new User();
        user.setName("Josef Novák");
        user.setEmail("admin@lost.com");
        user.setUserRole(UserRole.Administrator);
        user.setPassword("1245");
        
        user2 = new User();
        user2.setName("Monika Bledá");
        user2.setEmail("user@lost.com");
        user2.setUserRole(UserRole.Member);
        user2.setPassword("1245");
    }
    
    @Test
    public void testCreateNulltname() { 
        try {
           user.setName(null);
           userDao.create(user);
           fail( "Method didn't throw when I expected it to" );
           } catch (ConstraintViolationException e) {
            
           }
    }
   
    @Test
    public void testCreateNullEmail() {    
           try {
               user.setEmail(null);
               userDao.create(user);
           fail( "Method didn't throw when I expected it to" );
           } catch (ConstraintViolationException e) {
            
           }

    }
    
    @Test
    public void testCreateNullPassword() {                        
           try {
            user.setPassword(null);
            userDao.create(user);
        fail( "Method didn't throw when I expected it to" );
        } catch (ConstraintViolationException e) {

        }
        
    }
        
    @Test
    public void testCreateShortPassword() {    
           try {
               user.setPassword("123");
               userDao.create(user);
           fail( "Method didn't throw when I expected it to" );
           } catch (ConstraintViolationException e) {
            
           }

    }
    
    @Test
    public void testCreate() {                        
        Assert.assertEquals(0,user.getId());
        userDao.create(user);
        Assert.assertNotNull(user.getId());
    }
    
    @Test
    public void testFindById() {
                               
        Assert.assertEquals(0,user.getId());
        userDao.create(user);
        Assert.assertNotNull(user.getId());
                             
        User userFromDb = userDao.findById(user.getId());               
        Assert.assertEquals(userFromDb.getId(), user.getId());                
    }
    
    @Test
    public void testDelete() {                        
        userDao.create(user);
        userDao.create(user2);
        Assert.assertNotNull(user.getId());
        Assert.assertNotNull(user2.getId());
        Long userId = user.getId();
        Long user2Id = user2.getId();
        user = userDao.findById(userId);
        Assert.assertNotNull(user);  
        userDao.delete(user);
        user = userDao.findById(userId);
        Assert.assertNull(user);       
        user2 = userDao.findById(user2Id);
        Assert.assertNotNull(user2);       
    }
}
