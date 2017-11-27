/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dao.ItemDao;
import cz.muni.fi.pa165.dto.QuestionsDTO;
import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.enums.ItemColor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
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
    private Category clothes;
    private Category electronics;
    
    @BeforeMethod
    public void createCategories() {
        
        clothes = new Category();
        clothes.setName("Clothes");
        clothes.setDescription("Any kind of clothing"); 
        categoryService.create(clothes);
        jacket = new Item();
        jacket.setColor(ItemColor.BLUE);        
        jacket.setName("Jacket");
        jacket.setDescription("Jacket for men");               
        jacket.setCategory(clothes);
        jacket.setDepth(5);
        jacket.setWidth(5);
        jacket.setHeight(5);
        jacket.setWeight(BigDecimal.ONE);
        
        electronics = new Category();
        electronics.setName("Electronics");
        electronics.setDescription("Various types of electronics");
        categoryService.create(electronics);
        laptop = new Item();
        laptop.setColor(ItemColor.BLACK);
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
    
    @Test
    public void testItemReturned() {
        LocalDate date =  LocalDate.now();
        itemService.itemReturnedToOwner(jacket, date); 
        Assert.assertEquals(jacket.getReturned(), date);
    } 
    
    
    @Test
    public void testDelete() {
        itemService.delete(jacket);       
        verify(itemDao, times(1)).delete(jacket);
    } 
    @Test
    public void testFindByCategory() {
        List<Item> items = new ArrayList();
        items.add(jacket);
        when(itemDao.findByCategory(clothes)).thenReturn(items);
        List<Item> returnedItems = itemService.findByCategory(clothes);
        
        verify(itemDao, times(1)).findByCategory(clothes);
    } 
    @Test
    public void testFindById() {   
        when(itemDao.findById(0)).thenReturn(jacket);
        
        Item returnedItem = itemService.findById(0);
        Assert.assertEquals(returnedItem, jacket); 

        verify(itemDao, times(1)).findById(0);
    } 
    
    @Test
    public void testItemReturnedToOwner() {   
        Item returnedItem = itemService.findById(0);
        QuestionsDTO question = new QuestionsDTO();
        question.setColor(cz.muni.fi.pa165.dto.enums.ItemColor.BLUE);
        question.setDepth(5);
        question.setWidth(5);
        question.setHeight(5);
        question.setWeight(BigDecimal.ONE);
        question.setItemId(0);
        
        Assert.assertTrue(itemService.canBeReturned(question)); 
        
        question.setHeight(20);
        Assert.assertFalse(itemService.canBeReturned(question)); 
    } 
    
    @Test
    public void testUpdate() {   
        when(itemDao.findById(0)).thenReturn(laptop);
        
        Item laptop2 = itemDao.findById(0);
        laptop2.setColor(ItemColor.GREEN);
        laptop2.setName("Notebook");
        laptop2.setDescription("DELL laptop");
        laptop2.setDepth(1);
        laptop2.setWeight(BigDecimal.ONE);
        laptop2.setHeight(1);
        laptop2.setWidth(1);
        laptop2.setPhotoUri("test");
        
        Item returnedItem = itemService.update(laptop2);
        Assert.assertEquals(returnedItem, laptop2); 
    } 
    
    @Test
    public void testFindAll() {
        List<Item> items = new ArrayList();
        items.add(jacket);
        items.add(laptop);  
        
        when(itemDao.findAll()).thenReturn(items);
        
        List<Item> retrievedItems = itemService.findAll();
        Assert.assertEquals(2, retrievedItems.size());
        Assert.assertEquals(retrievedItems.get(0), jacket);
        Assert.assertEquals(retrievedItems.get(1), laptop);
        
        
        verify(itemDao, times(1)).findAll();
    } 
}
