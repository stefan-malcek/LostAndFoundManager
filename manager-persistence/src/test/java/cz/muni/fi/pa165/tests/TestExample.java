package cz.muni.fi.pa165.tests;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.enums.UserRole;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Stefan Malcek
 */
@Test
@ContextConfiguration(classes = {PersistenceApplicationContext.class})
public class TestExample extends AbstractTestNGSpringContextTests {

    private static final String USER_NAME = "Hello from DB!";

    @PersistenceUnit
    private EntityManagerFactory emf;
    private User user;

    @BeforeClass
    public void setup() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        user = new User();
        user.setName(USER_NAME);
        user.setEmail("example@gmail.com");
        user.setUserRole(UserRole.Administrator);

        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testDescription() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User storedUser = em.find(User.class, user.getId());
        em.getTransaction().commit();
        em.close();

        Assert.assertEquals(USER_NAME, storedUser.getName());
    }
}
