package cz.muni.fi.pa165.tests;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entities.Temp;

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
public class TempTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;
    private static final String description = "Hello from DB!";

    private Temp temp;

    @BeforeClass
    public void setup() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        temp = new Temp();
        temp.setDescription(description);

        em.persist(temp);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testDescription() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Temp storedTemp = em.find(Temp.class, temp.getId());
        em.getTransaction().commit();
        em.close();

        Assert.assertEquals(description, storedTemp.getDescription());
    }
}
