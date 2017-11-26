/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.dao.ItemDao;
import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.entities.Item;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author robhavlicek
 */
@Service
public class ItemServiceImpl implements ItemService { 

    @Inject 
    private ItemDao itemDao;
    
    @Override
    public void create(Item item) {
        itemDao.create(item);
    }

    @Override
    public void delete(Item item) {
        itemDao.delete(item);
    }

    @Override
    public Item findById(long id) {
       return itemDao.findById(id);
    }

    @Override
    public List<Item> findByCategory(Category category) {
        return itemDao.findByCategory(category);
    }

    @Override
    public List<Item> findAll() {
        return itemDao.findAll();
    }
    
}
