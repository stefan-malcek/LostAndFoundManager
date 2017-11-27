/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CategoryDTO;
import cz.muni.fi.pa165.dto.ItemDTO;
import cz.muni.fi.pa165.dto.QuestionsDTO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author robhavlicek
 */
public interface ItemFacade {
    /**
     * Stores a new item in the application.
     *
     * @param item item to create
     */
    Long create(ItemDTO item);

    /**
     * Deletes the item with given id.
     *
     * @param item item to delete
     */
    void remove(ItemDTO item);

    /**
     * Retrieves the item with given id.
     *
     * @param id Id of the item
     * @return {@code Item} with given id
     */
    ItemDTO findById(long id);

    /**
     * Retrieves the items with given category.
     *
     * @param category category of the items
     * @return {@code List} of items
     */
    List<ItemDTO> findByCategory(CategoryDTO category);

    /**
     * Retrieves all items.
     *
     * @return {@code List} of items
     */
    List<ItemDTO> getAllItems();
    
    
    /**
     * Set date when item was returned to owner.
     *
     *  @param item item which was returned
     *  @param date date of return
     * 
     */
    void itemReturnedToOwner(ItemDTO item, Date date);
    
    /**
     * Set date when item was returned to owner.
     *
     *  @param updatedItem item with new values
     * 
     *  @return updated item
     */
    ItemDTO update(ItemDTO updatedItem );
    
    public boolean canBeReturned(QuestionsDTO questions);

}
