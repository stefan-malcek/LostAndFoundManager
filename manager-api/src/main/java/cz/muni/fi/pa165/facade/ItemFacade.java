/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.ItemCreateDTO;
import cz.muni.fi.pa165.dto.ItemDTO;
import cz.muni.fi.pa165.dto.QuestionsDTO;
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
    Long create(ItemCreateDTO item);

    /**
     * Deletes the item with given id.
     *
     * @param id item to delete
     */
    void remove(long id);

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
     * @param categoryId category of the items
     * @return {@code List} of items
     */
    List<ItemDTO> findByCategory(long categoryId);

    /**
     * Retrieves all items.
     *
     * @return {@code List} of items
     */
    List<ItemDTO> getAllItems();
    
    
    /**
     * Set date when item was returned to owner.
     *
     *  @param id item which was returned
     * 
     */
    void itemReturnedToOwner(long id);
    
    /**
     * Set date when item was returned to owner.
     *
     *  @param updatedItem item with new values
     * 
     *  @return updated item
     */
    ItemDTO update(ItemDTO updatedItem );
    
    public boolean canBeReturned(long id, QuestionsDTO questions);

}
