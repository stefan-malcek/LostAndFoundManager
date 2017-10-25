package cz.muni.fi.pa165.tests;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.enums.UserRole;

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
 * @author Stefan Malcek
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ExampleDaoTest extends AbstractTestNGSpringContextTests {

    private static final String USER_NAME = "Hello from DB!";

    @Autowired
    private UserDao userDao;
    
    private User user;

    @BeforeMethod
    public void setup() {
        user = new User();
        user.setName(USER_NAME);
        user.setEmail("example@gmail.com");
        user.setUserRole(UserRole.Administrator);
        
        userDao.create(user);
    }

    @Test
    public void test() {
        User storedUser = userDao.findById(user.getId());
        
        Assert.assertEquals(USER_NAME, storedUser.getName());
    }
}