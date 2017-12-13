/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.dto.QuestionsDTO;
import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.entities.Item;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author robhavlicek
 */
@Service
public interface ItemService {
  
    /**
     * Stores a new item in the application.
     *
     * @param item item to create
     */
    void create(Item item);

    /**
     * Deletes the item with given id.
     *
     * @param item item to delete
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
    
    /**
     * Set date when item was returned to owner.
     *
     *  @param item item which was returned
     *  @param date date of return
     * 
     */
    void itemReturnedToOwner(Item item, Date date);
    
    /**
     * Set date when item was returned to owner.
     *
     *  @param updatedItem updated item
     * 
     */
    Item update(Item updatedItem );
    
    
    /**
     * When administrator returns item, he has to check if returned item is actually users. User cant see field like width, height, ... 
     *
     *  @param  questions users answers and id of returned object
     * 
     */
    public boolean canBeReturned(long id, QuestionsDTO questions);
}
