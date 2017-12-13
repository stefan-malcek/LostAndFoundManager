/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.dto.ItemCreateDTO;
import cz.muni.fi.pa165.dto.ItemDTO;
import cz.muni.fi.pa165.dto.QuestionsDTO;
import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.facade.ItemFacade;
import cz.muni.fi.pa165.services.CategoryService;
import cz.muni.fi.pa165.services.ItemService;
import cz.muni.fi.pa165.services.MappingService;
import cz.muni.fi.pa165.services.TimeService;
import exceptions.LostAndFoundManagerDataAccessException;
import java.util.Date;
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
    private CategoryService categoryService;

    @Inject
    private TimeService timeService;

    @Inject
    private MappingService mappingService;

    @Override
    public Long create(ItemCreateDTO itemDTO) {
        try {

            Item item = mappingService.mapTo(itemDTO, Item.class);
            itemService.create(item);
            return item.getId();

        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("can not create item", e);
        }
    }

    @Override
    public void remove(long id) {
        try {
            Item item = new Item();
            item.setId(id);
            itemService.delete(item);

        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("can not remove item", e);
        }
    }

    @Override
    public ItemDTO findById(long id) {
        try {

            Item item = itemService.findById(id);
            return (item == null) ? null : mappingService.mapTo(item, ItemDTO.class);

        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("can not find item by id", e);
        }
    }

    @Override
    public List<ItemDTO> findByCategory(long categoryId) {

        try {

            Category category = categoryService.findById(categoryId);
            List<Item> items = itemService.findByCategory(category);
            return (items == null) ? null : mappingService.mapTo(items, ItemDTO.class);

        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("can not find items by category", e);
        }
    }

    @Override
    public boolean canBeReturned(long id, QuestionsDTO questions) {

        try {
            return itemService.canBeReturned(id, questions);
        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("can not evalute if item can be returned", e);
        }
    }

    @Override
    public List<ItemDTO> getAllItems() {
        try {
            List<Item> allItems = itemService.findAll();
            return mappingService.mapTo(allItems, ItemDTO.class);

        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("can not retrive all items", e);
        }
    }

    @Override
    public void itemReturnedToOwner(long id) {
        try {
            Item item = itemService.findById(id);

            if (item.getReturned() != null) {
                throw new IllegalArgumentException("Item was already returned.");
            }

            Date actualDate = timeService.getCurrentTime();
            itemService.itemReturnedToOwner(item, actualDate);

        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("can not set return date", e);
        }
    }

    @Override
    public ItemDTO update(ItemDTO updatedItem) {
        try {
            Item updated = mappingService.mapTo(updatedItem, Item.class);
            return mappingService.mapTo(itemService.update(updated), ItemDTO.class);

        } catch (Exception e) {
            throw new LostAndFoundManagerDataAccessException("can not update item", e);
        }
    }
}
