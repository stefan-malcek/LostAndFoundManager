package cz.muni.fi.pa165.tests;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.dao.CategoryDao;
import cz.muni.fi.pa165.dao.ItemDao;
import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.enums.ItemColor;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * A set of tests for Item DAO
 * @author Šimon Baláž
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ItemDaoTest extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private ItemDao itemDao;
    
    @Autowired
    private CategoryDao categoryDao;
    
    private Item jacket;
    private Item laptop;

    @BeforeMethod
    public void setup() {
        Category clothes = new Category();
        clothes.setName("Clothes");
        clothes.setDescription("Any kind of clothing"); 
        categoryDao.create(clothes);
        jacket = new Item();
        jacket.setColor(ItemColor.BLUE);        
        jacket.setName("Jacket");
        jacket.setDescription("Jacket for men");               
        jacket.setCategory(clothes);
        
        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setDescription("Various types of electronics");
        categoryDao.create(electronics);
        laptop = new Item();
        laptop.setColor(ItemColor.BLACK);
        laptop.setName("Laptop");
        laptop.setDescription("Lenovo laptop");        
        laptop.setCategory(electronics);
    }
        
    @Test
    public void testCreate() {
        Assert.assertEquals(0, jacket.getId());        
        itemDao.create(jacket);       
        Assert.assertTrue(0 != jacket.getId());
    }   
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateNullName() {        
        jacket.setName(null);
        itemDao.create(jacket);
    }   
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateEmptyName() {        
        jacket.setName("");
        itemDao.create(jacket);
    } 
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateOverlongName() {
        // Generated name will be more than 256 characters long
        StringBuilder name = new StringBuilder() ;
        for(int i = 0; i <= 122; i++) {
            name.append(i);
        }       
             
        jacket.setName(name.toString());
        itemDao.create(jacket);
    } 
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateNullDescription() {
        jacket.setDescription(null);
        itemDao.create(jacket);
    }   
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateNegativeHeight() {                 
        jacket.setHeight(-1);           
        itemDao.create(jacket);
    } 
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateNegativeWidth() {
        jacket.setWidth(-1);          
        itemDao.create(jacket);
    } 
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateNegativeDepth() {
        jacket.setDepth(-1);            
        itemDao.create(jacket);
    } 
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateNegativeWeight() {
        jacket.setWeight(new BigDecimal(-1.00));
        itemDao.create(jacket);
    } 
    
    @Test
    public void testFindById() {
        itemDao.create(jacket);
        
        Item storedJacket = itemDao.findById(jacket.getId());
        Assert.assertEquals(storedJacket.getId(), jacket.getId());
    }
    
    @Test
    public void testFindByIdNegative() {        
        try {
            itemDao.findById(-50L);
            Assert.fail("Expected exception to be thrown");
        } catch (Exception ex) {
            Assert.assertTrue(ex.getCause() instanceof IllegalArgumentException);
        }
    }
    
    @Test
    public void testFindByIdZero() {        
        try {
            itemDao.findById(0);
            Assert.fail("Expected exception to be thrown");
        } catch (Exception ex) {            
            Assert.assertTrue(ex.getCause() instanceof IllegalArgumentException);
        }
    }
    
    @Test
    public void testFindByCategory() {
        itemDao.create(jacket);
        
        List<Item> storedItems = itemDao.findByCategory(jacket.getCategory());
        Assert.assertEquals(storedItems.size(), 1);
        Assert.assertEquals(storedItems.get(0), jacket);
    }
    
    @Test
    public void testFindByCategoryNull() {        
        try {
            itemDao.findByCategory(null);
            Assert.fail("Expected exception to be thrown");
        } catch (Exception ex) {            
            Assert.assertTrue(ex.getCause() instanceof IllegalArgumentException);
        }
    }
    
    @Test
    public void testFindAll() {
        itemDao.create(jacket);
        itemDao.create(laptop);
        
        List<Item> storedItems = itemDao.findAll();
        Assert.assertEquals(storedItems.size(), 2);
        Assert.assertEquals(storedItems.get(0), jacket);
        Assert.assertEquals(storedItems.get(1), laptop);
    }
    
    @Test
    public void testDelete() {
        itemDao.create(jacket);
        itemDao.create(laptop);
        
        List<Item> storedItems = itemDao.findAll();
        Assert.assertEquals(storedItems.size(), 2);        
        itemDao.delete(storedItems.get(0));    
        
        storedItems = itemDao.findAll();
        Assert.assertEquals(storedItems.size(), 1);
        Assert.assertEquals(storedItems.get(0), laptop);               
    }
    
    @Test
    public void testDeleteNullItem() {        
        try {
            itemDao.delete(null);
            Assert.fail("Expected exception to be thrown");
        } catch (Exception ex) {            
            Assert.assertTrue(ex.getCause() instanceof IllegalArgumentException);
        }
    }
    
}
