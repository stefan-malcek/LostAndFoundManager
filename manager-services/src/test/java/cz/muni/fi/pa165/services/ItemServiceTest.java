/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dao.ItemDao;
import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.enums.ItemColor;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author robhavlicek
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class ItemServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private ItemDao itemDao;
    
    @Autowired
    @InjectMocks
    private ItemService itemService;
    
    @Mock
    private CategoryService categoryService;
    
    private Item jacket;
    private Item laptop;
    
    @BeforeMethod
    public void createCategories() {
        
        Category clothes = new Category();
        clothes.setName("Clothes");
        clothes.setDescription("Any kind of clothing"); 
        categoryService.create(clothes);
        jacket = new Item();
        jacket.setColor(ItemColor.Blue);        
        jacket.setName("Jacket");
        jacket.setDescription("Jacket for men");               
        jacket.setCategory(clothes);
        
        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setDescription("Various types of electronics");
        categoryService.create(electronics);
        laptop = new Item();
        laptop.setColor(ItemColor.Black);
        laptop.setName("Laptop");
        laptop.setDescription("Lenovo laptop");        
        laptop.setCategory(electronics);

    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testCreate() {
        itemService.create(jacket);       
        verify(itemDao, times(1)).create(jacket);
    }  
}
