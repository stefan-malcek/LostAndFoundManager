/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.dao.ItemDao;
import cz.muni.fi.pa165.dao.CategoryDao;
import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.dto.QuestionsDTO;
import cz.muni.fi.pa165.entities.Item;
import java.math.BigDecimal;
import java.util.Date;
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
    
    @Inject 
    private CategoryDao categoryDao;
    
    
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
    public void itemReturnedToOwner(Item item, Date date) {
       if(date != null)
        {
            item.setReturned(date);
        }
    }
    
    @Override
    public List<Item> findByCategory(Category category) {
        return itemDao.findByCategory(category);
    }

    @Override
    public List<Item> findAll() {
        return itemDao.findAll();
    }
    
    @Override
    public Item update(Item updatedItem ) {
       Item updated = itemDao.findById(updatedItem.getId());
       updated.setColor(updatedItem.getColor());
       updated.setDepth(updatedItem.getDepth());
       updated.setWidth(updatedItem.getWidth());
       updated.setWeight(updatedItem.getWeight());
       updated.setName(updatedItem.getName());
       updated.setPhotoUri(updatedItem.getPhotoUri());
       updated.setDescription(updatedItem.getDescription());
       updated.setHeight(updatedItem.getHeight());
       return updated;
    }
    
    private boolean isNotAroundValue(int x, int y) {
        return !(y-5<=x && x<=y+5);
    }

    @Override
    public boolean canBeReturned(QuestionsDTO questions) {
       
        Item item =  itemDao.findById(questions.getItemId());

       if(item.getColor().name() != questions.getColor().name())
           return false;
       if(isNotAroundValue(item.getDepth(), questions.getDepth()))
           return false;
       if(isNotAroundValue(item.getWidth(), questions.getWidth()))
           return false;
       if(isNotAroundValue(item.getHeight(), questions.getHeight()))
           return false; 
        if(item.getWeight().add(BigDecimal.TEN).compareTo(questions.getWeight()) > 0 && item.getWeight().add(BigDecimal.TEN.negate()).compareTo(questions.getWeight()) > 0 )
           return false; 
       
       return true;
    }
    
}
