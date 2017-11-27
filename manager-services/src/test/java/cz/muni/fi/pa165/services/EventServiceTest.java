package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.ServiceApplicationContext;
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
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Adam Bananka
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class EventServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private EventDao eventDao;
    @Mock
    private UserDao userDao;
    @Mock
    private ItemDao itemDao;
    @Mock
    private CategoryDao categoryDao;

    private Event lossEvent;
    private Event findEvent;
    private User user;

    @Autowired
    @InjectMocks
    private EventService eventService;

    @BeforeMethod
    public void createData() {
        user = new User();
        user.setName("Tester");
        user.setEmail("mail@test.com");
        user.setUserRole(UserRole.MEMBER);
        userDao.create(user);
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("electro stuff");
        categoryDao.create(category);
        Item laptop = new Item();
        laptop.setColor(ItemColor.GRAY);
        laptop.setName("Laptop");
        laptop.setDescription("Lenovo laptop");
        laptop.setCategory(category);
        itemDao.create(laptop);
        Item mobile = new Item();
        mobile.setName("Mobile");
        mobile.setColor(ItemColor.WHITE);
        mobile.setDescription("Samsung");
        mobile.setCategory(category);
        itemDao.create(mobile);

        lossEvent = new Event();
        lossEvent.setItem(laptop);
        lossEvent.setOwner(user);
        lossEvent.setDateOfLoss(LocalDate.now().minusDays(2));
        lossEvent.setPlaceOfLoss("Brno");
        findEvent = new Event();
        findEvent.setItem(mobile);
        findEvent.setFinder(user);
        findEvent.setDateOfFind(LocalDate.now().minusDays(1));
        findEvent.setPlaceOfFind("Brno");
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreateEvent() {
        Assert.assertEquals(0, lossEvent.getId());
        eventService.createEvent(lossEvent);
        Assert.assertTrue(lossEvent.getId() != 0);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateEventNull() {
        eventService.createEvent(null);
    }

    @Test
    public void testDeleteEvent() {
        eventService.createEvent(lossEvent);
        eventService.createEvent(findEvent);

        Assert.assertEquals(eventService.findAllEvents().size(), 2);
        eventService.deleteEvent(findEvent);

        Assert.assertEquals(eventService.findAllEvents().size(), 1);
        Assert.assertEquals(eventService.findAllEvents().get(0), lossEvent);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteEventNull() {
        eventService.deleteEvent(null);
    }

    @Test
    public void testAddLoosing() {
        eventService.createEvent(findEvent);
        Event event = eventService.findEventById(findEvent.getId());
        Assert.assertEquals(null, event.getOwner());

        eventService.addLoosing(findEvent, user, LocalDate.now().minusDays(2), "Brno");
        event = eventService.findEventById(findEvent.getId());
        Assert.assertTrue(event.getOwner() != null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddLoosingAlreadyLost() {
        eventService.createEvent(lossEvent);
        eventService.addLoosing(lossEvent, user, LocalDate.now().minusDays(2), "Brno");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddLoosingNullOwner() {
        eventService.addLoosing(findEvent, null, LocalDate.now().minusDays(2), "Brno");
    }

    @Test
    public void testAddFinding() {
        eventService.createEvent(lossEvent);
        Event event = eventService.findEventById(lossEvent.getId());
        Assert.assertEquals(null, event.getFinder());

        eventService.addFinding(lossEvent, user, LocalDate.now().minusDays(1), "Brno");
        event = eventService.findEventById(lossEvent.getId());
        Assert.assertTrue(event.getFinder() != null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddFindingAlreadyFound() {
        eventService.addFinding(findEvent, user, LocalDate.now().minusDays(1), "Brno");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddFindingNullFinder() {
        eventService.addFinding(findEvent, null, LocalDate.now().minusDays(1), "Brno");
    }

    @Test
    public void testCheckEventResolved() {
        Assert.assertFalse(eventService.checkEventResolved(lossEvent));
        lossEvent.setFinder(user);
        lossEvent.setPlaceOfFind("Brno");
        lossEvent.setDateOfFind(LocalDate.now());
        Assert.assertTrue(eventService.checkEventResolved(lossEvent));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCheckEventResolvedNullEvent() {
        eventService.checkEventResolved(null);
    }

    @Test
    public void testFindEventById() {
        eventService.createEvent(lossEvent);
        eventService.createEvent(findEvent);
        Assert.assertEquals(lossEvent, eventService.findEventById(lossEvent.getId()));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindEventByIdZero() {
        eventService.findEventById(0);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindEventByIdNegative() {
        eventService.findEventById(-7);
    }

    @Test
    public void testFindEventByItem() {
        eventService.createEvent(lossEvent);
        eventService.createEvent(findEvent);
        Assert.assertEquals(lossEvent, eventService.findEventByItem(lossEvent.getItem()));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindEventByItemNull() {
        eventService.findEventByItem(null);
    }

    @Test
    public void testFindAllEvents() {
        eventService.createEvent(lossEvent);
        eventService.createEvent(findEvent);
        Assert.assertEquals(eventService.findAllEvents().size(), 2);
    }

    @Test
    public void testFindEventsByFinder() {
        eventService.createEvent(lossEvent);
        eventService.createEvent(findEvent);
        List<Event> res = eventService.findEventsByFinder(findEvent.getFinder());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(findEvent, res.get(0));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindEventsByFinderNull() {
        eventService.findEventsByFinder(null);
    }

    @Test
    public void testFindEventsByOwner() {
        eventService.createEvent(lossEvent);
        eventService.createEvent(findEvent);
        List<Event> res = eventService.findEventsByOwner(lossEvent.getOwner());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lossEvent, res.get(0));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testFindEventsByOwnerNull() {
        eventService.findEventsByOwner(null);
    }

    @Test
    public void testFindEventsByPlaceOfLoss() {
        eventService.createEvent(lossEvent);
        eventService.createEvent(findEvent);
        List<Event> res = eventService.findEventsByPlaceOfLoss(lossEvent.getPlaceOfLoss());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lossEvent, res.get(0));
    }

    @Test
    public void testFindEventsByPlaceOfFind() {
        eventService.createEvent(lossEvent);
        eventService.createEvent(findEvent);
        List<Event> res = eventService.findEventsByPlaceOfFind(findEvent.getPlaceOfFind());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(findEvent, res.get(0));
    }

    @Test
    public void testFindEventsByDateOfFind() {
        eventService.createEvent(lossEvent);
        eventService.createEvent(findEvent);
        List<Event> res = eventService.findEventsByDateOfFind(findEvent.getDateOfFind());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(findEvent, res.get(0));
    }

    @Test
    public void testFindEventsByDateOfLoss() {
        eventService.createEvent(lossEvent);
        eventService.createEvent(findEvent);
        List<Event> res = eventService.findEventsByDateOfLoss(lossEvent.getDateOfLoss());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lossEvent, res.get(0));
    }
}
