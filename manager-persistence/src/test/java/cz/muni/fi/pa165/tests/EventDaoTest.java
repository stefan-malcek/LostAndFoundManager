package cz.muni.fi.pa165.tests;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.dao.CategoryDao;
import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.dao.ItemDao;
import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.enums.ItemColor;
import cz.muni.fi.pa165.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;

/**
 * A set of tests for Event DAO.
 *
 * @author Adam Bananka
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class EventDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private EventDao eventDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private CategoryDao categoryDao;

    private Event lost;
    private Event found;

    @BeforeMethod
    public void setup() {
        User user = new User();
        user.setName("Monika Bledá");
        user.setEmail("user@lost.com");
        user.setUserRole(UserRole.Member);
        user.setPasswordHash("1245");
        userDao.create(user);
        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setDescription("Various types of electronics");
        categoryDao.create(electronics);
        Item laptop = new Item();
        laptop.setColor(ItemColor.Black);
        laptop.setName("Laptop");
        laptop.setDescription("Lenovo laptop");
        laptop.setCategory(electronics);
        itemDao.create(laptop);
        Item mobile = new Item();
        mobile.setName("Mobile");
        mobile.setDescription("Samsung");
        mobile.setCategory(electronics);
        itemDao.create(mobile);

        lost = new Event();
        lost.setItem(laptop);
        lost.setOwner(user);
        lost.setDateOfLoss(LocalDate.now().minusDays(2));
        lost.setPlaceOfLoss("Botanická, Brno");
        found = new Event();
        found.setItem(mobile);
        found.setFinder(user);
        found.setDateOfFind(LocalDate.now().minusDays(1));
        found.setPlaceOfFind("Brno");
    }

    @Test
    public void testCreate(){
        Assert.assertEquals(0, lost.getId());
        eventDao.create(lost);
        Assert.assertTrue(lost.getId() != 0);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateNull(){
        eventDao.create(null);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void testCreateNullItem(){
        lost.setItem(null);
        eventDao.create(lost);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateNonUniqueItem() {
        eventDao.create(lost);
        found.setItem(lost.getItem());
        eventDao.create(found);
    }

    @Test
    public void testDelete(){
        eventDao.create(lost);
        eventDao.create(found);

        Assert.assertEquals(eventDao.findAll().size(), 2);
        eventDao.delete(found);

        Assert.assertEquals(eventDao.findAll().size(), 1);
        Assert.assertEquals(eventDao.findAll().get(0), lost);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteNull(){
        eventDao.delete(null);
    }

    @Test
    public void testFindById(){
        eventDao.create(lost);
        eventDao.create(found);
        Assert.assertEquals(lost, eventDao.findById(lost.getId()));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindByIdZero(){
        eventDao.create(lost);
        eventDao.findById(0L);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindByIdNegative(){
        eventDao.create(lost);
        eventDao.findById(-7L);
    }

    @Test
    public void testFindByItem(){
        eventDao.create(lost);
        eventDao.create(found);
        Assert.assertEquals(lost, eventDao.findByItem(lost.getItem()));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindByItemNull(){
       eventDao.findByItem(null);
    }

    @Test
    public void testFindAll(){
        eventDao.create(lost);
        eventDao.create(found);
        Assert.assertEquals(2, eventDao.findAll().size());
    }

    @Test
    public void testFindEventByFinder(){
        eventDao.create(lost);
        eventDao.create(found);
        List<Event> res = eventDao.findEventByFinder(found.getFinder());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(found, res.get(0));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindEventByFinderNull(){
        eventDao.findEventByFinder(null);
    }

    @Test
    public void testFindEventByOwner(){
        eventDao.create(lost);
        eventDao.create(found);
        List<Event> res = eventDao.findEventByOwner(lost.getOwner());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lost, res.get(0));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindEventByOwnerNull(){
        eventDao.findEventByOwner(null);
    }

    @Test
    public void testFindEventByPlaceOfLoss(){
        eventDao.create(lost);
        eventDao.create(found);
        List<Event> res = eventDao.findEventByPlaceOfLoss(lost.getPlaceOfLoss());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lost, res.get(0));
    }

    @Test
    public void testFindEventByPlaceOfFind(){
        eventDao.create(lost);
        eventDao.create(found);
        List<Event> res = eventDao.findEventByPlaceOfFind(found.getPlaceOfFind());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(found, res.get(0));
    }

    @Test
    public void testFindEventByDateOfFind(){
        eventDao.create(lost);
        eventDao.create(found);
        List<Event> res = eventDao.findEventByDateOfFind(found.getDateOfFind());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(found, res.get(0));
    }

    @Test
    public void testFindEventByDateOfLoss(){
        eventDao.create(lost);
        eventDao.create(found);
        List<Event> res = eventDao.findEventByDateOfLoss(lost.getDateOfLoss());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lost, res.get(0));
    }
}
