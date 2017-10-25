package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.enums.UserRole;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Represents a main class.
 * @author Stefan Malcek
 */
public class MainJavaSe {

    public static void main(String[] args) throws SQLException {
        // The following line is here just to start up a in-memory database
        new AnnotationConfigApplicationContext(PersistenceApplicationContext.class);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        User user = new User();
        user.setName("temp name");
        user.setEmail("example@gmail.com");
        user.setUserRole(UserRole.Administrator);

        em.persist(user);
        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();
        em.getTransaction().begin();

        User storedUser = em.find(User.class, 1L);

        em.getTransaction().commit();
        em.close();
        emf.close();

        System.out.println(storedUser);
    }
}