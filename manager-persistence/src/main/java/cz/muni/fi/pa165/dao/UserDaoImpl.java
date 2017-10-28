package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Implements {@code UserDao} interface.
 *
 * @author Stefan Malcek
 */
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        em.persist(user);
    }

    @Override
    public void delete(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        em.remove(user);
    }

    @Override
    public User findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id cannot be lower than or equal to zero");
        }

        return em.find(User.class, id);
    }

    @Override
    public User findUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Invalid email value.");
        }

        List<User> users = em.createQuery("SELECT u FROM User u WHERE u.email = :email",
                User.class).setParameter("email", email).getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
    
    @Override
    public void update(User user) {
        if (user == null) 
            throw new IllegalArgumentException("User cannot be null.");
         em.merge(user); 
    }
}
