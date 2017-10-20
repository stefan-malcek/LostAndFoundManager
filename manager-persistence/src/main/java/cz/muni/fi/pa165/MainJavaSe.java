/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entities.Temp;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Stefan Malcek
 */
public class MainJavaSe {

    public static void main(String[] args) throws SQLException {
        // The following line is here just to start up a in-memory database
        new AnnotationConfigApplicationContext(InMemoryDatabaseSource.class);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Temp temp = new Temp();
        temp.setDescription("Hello from DB.");
        em.persist(temp);
        
        em.getTransaction().commit();
        em.close();
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        Temp storedTemp = em.find(Temp.class, 1L);
        em.getTransaction().commit();
        System.out.println(storedTemp);
        em.close();
        
        emf.close();
    }
}
