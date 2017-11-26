package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Category;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Implements {@code CategoryDao} interface.
 * @author Šimon Baláž
 */
@Repository
public class CategoryDaoImpl implements CategoryDao {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }

        em.persist(category);
    }

    @Override
    public void delete(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }

        em.remove(em.contains(category) ? category : em.merge(category));
    }

    @Override
    public Category findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id cannot be lower than or equal to zero.");
        }

        return em.find(Category.class, id);
    }

    @Override
    public Category findCategoryByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid name.");
        }
               
        List<Category> categories = em.createQuery("SELECT c FROM Category c WHERE "
                + "c.name = :name", Category.class).setParameter("name", name).getResultList();
                
        if (categories == null || categories.isEmpty()) {
            return null;
        }
        
        return categories.get(0);
        
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }
    
}
