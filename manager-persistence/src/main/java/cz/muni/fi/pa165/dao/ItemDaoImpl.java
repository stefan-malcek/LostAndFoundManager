package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.entities.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implements {@code ItemDao} interface.
 *
 * @author Adam Bananka
 */
@Repository
public class ItemDaoImpl implements ItemDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }

        em.persist(item);
    }

    @Override
    public void delete(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }

        em.remove(item);
    }

    @Override
    public Item findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id cannot be lower than or equal to zero");
        }

        return em.find(Item.class, id);
    }

    @Override
    public List<Item> findByCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }
        return em.createQuery("SELECT i FROM Item i WHERE i.category = :category", Item.class)
                .setParameter("category", category)
                .getResultList();
    }

    @Override
    public List<Item> findAll() {
        return em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
    }
}
