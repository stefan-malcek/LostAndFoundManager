package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Category;
import java.util.List;

/**
 * Represents a Data Access Object for Category entity.
 * @author Šimon Baláž
 */
public interface CategoryDao {
    
    /**
     * Stores a new category in the application.
     * @param category Category to create
     */
    void create(Category category);
    
    /**
     * Deletes the category with given id.
     * @param category Category to delete
     */
    void delete(Category category);
    
    /**
     * Retrieves the category with given id.
     * @param id Id of the category
     * @return {@code Category} with given id
     */
    Category findById(long id);
    
    /**
     * Retrieves the category with given name.
     * @param name Name of the category
     * @return {@code Category} with given id 
     * or {@code null} if was not found. 
     */
    Category findCategoryByName(String name);
    
    /**
     * Retrieves all categories.
     * @return {@code List} of categories
     */
    List<Category> findAll();
    
}
