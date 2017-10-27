/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Category;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implements {@code CategoryDao} interface.
 * @author Šimon Baláž
 */
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

        em.remove(category);
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
                + "c.name = :name", Category.class).getResultList();
                
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
