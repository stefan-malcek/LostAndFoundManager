/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.dto.CategoryDTO;
import cz.muni.fi.pa165.dto.ItemDTO;
import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.facade.ItemFacade;
import cz.muni.fi.pa165.services.ItemService;
import cz.muni.fi.pa165.services.MappingService;
import exceptions.LostAndFoundManagerDataAccessException;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author robhavlicek
 */

@Service
@Transactional
public class ItemFacadeImpl implements ItemFacade {

    @Inject
    private ItemService itemService;

    @Inject
    private MappingService mappingService;
    
    @Override
    public Long create(ItemDTO itemDTO) {
        try {
            
            Item item = mappingService.mapTo(itemDTO, Item.class);
            itemService.create(item);
            return item.getId();
            
        } catch (Exception e) 
        {
            throw new LostAndFoundManagerDataAccessException("can not create item",e);
        }
    }

    @Override
    public void remove(ItemDTO itemDTO) {
        try {
            
            Item item = mappingService.mapTo(itemDTO, Item.class);
            itemService.delete(item);
            
        } catch (Exception e) 
        {
            throw new LostAndFoundManagerDataAccessException("can not remove item",e);
        }
    }

    @Override
    public ItemDTO findById(long id) {
        try {
            
            Item item = itemService.findById(id);
            return (item == null) ? null : mappingService.mapTo(item, ItemDTO.class);
            
        } catch (Exception e) 
        {
            throw new LostAndFoundManagerDataAccessException("can not find item by id",e);
        }
    }

    @Override
    public List<ItemDTO> findByCategory(CategoryDTO categoryDTO) {
        
        try {
            
            Category category = mappingService.mapTo(categoryDTO, Category.class);
            List<Item> items = itemService.findByCategory(category);
            return (items == null) ? null : mappingService.mapTo(items, ItemDTO.class);
            
        } catch (Exception e) 
        {
            throw new LostAndFoundManagerDataAccessException("can not find items by category",e);
        }
    }

    @Override
    public List<ItemDTO> getAllItems() {
        
        try {
            
            List<Item> allItems = itemService.findAll();
            return mappingService.mapTo(allItems, ItemDTO.class);
            
        } catch (Exception e) 
        {
            throw new LostAndFoundManagerDataAccessException("can not retrive all items",e);
        }
    }
    
}
