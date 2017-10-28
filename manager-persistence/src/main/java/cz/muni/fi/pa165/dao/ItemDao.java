package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.entities.Item;

import java.util.List;

/**
 * Represents a Data Access Object for Item entity.
 *
 * @author Adam Bananka
 */
public interface ItemDao {

    /**
     * Stores a new item in the application.
     *
     * @param item User to create
     */
    void create(Item item);

    /**
     * Deletes the item with given id.
     *
     * @param item User to delete
     */
    void delete(Item item);

    /**
     * Retrieves the item with given id.
     *
     * @param id Id of the item
     * @return {@code Item} with given id
     */
    Item findById(long id);

    /**
     * Retrieves the items with given category.
     *
     * @param category category of the items
     * @return {@code List} of items
     */
    List<Item> findByCategory(Category category);

    /**
     * Retrieves all items.
     *
     * @return {@code List} of items
     */
    List<Item> findAll();
}
