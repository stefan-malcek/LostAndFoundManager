package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.dto.StatisticsDTO;
import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.enums.ItemColor;
import cz.muni.fi.pa165.enums.StatisticsType;
import cz.muni.fi.pa165.enums.UserRole;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Adam Bananka
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class EventServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private EventDao eventDao;

    @Autowired
    @InjectMocks
    private EventService eventService;

    @Mock
    private UserService userService;
    @Mock
    private ItemService itemService;
    @Mock
    private CategoryService categoryService;

    private Event lossEvent;
    private Event findEvent;
    private List<Event> events;
    private User user;

    @BeforeMethod
    public void createData() {
        user = new User();
        user.setName("Tester");
        user.setEmail("mail@test.com");
        user.setUserRole(UserRole.MEMBER);
        userService.register(user, "psw12345");
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("electro stuff");
        categoryService.create(category);
        Item laptop = new Item();
        laptop.setColor(ItemColor.GRAY);
        laptop.setName("Laptop");
        laptop.setDescription("Lenovo laptop");
        laptop.setCategory(category);
        itemService.create(laptop);
        Item mobile = new Item();
        mobile.setName("Mobile");
        mobile.setColor(ItemColor.WHITE);
        mobile.setDescription("Samsung");
        mobile.setCategory(category);
        itemService.create(mobile);

        lossEvent = new Event();
        lossEvent.setItem(laptop);
        lossEvent.setOwner(user);
        lossEvent.setDateOfLoss(Date.from(Instant.now()));
        lossEvent.setPlaceOfLoss("Brno");
        findEvent = new Event();
        findEvent.setItem(mobile);
        findEvent.setFinder(user);
        findEvent.setDateOfFind(Date.from(Instant.now()));
        findEvent.setPlaceOfFind("Brno");

        events = new ArrayList<>();
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateEvent() {
        eventService.createEvent(lossEvent);
        verify(eventDao, atLeastOnce()).create(lossEvent);
    }

    @Test
    public void testDeleteEvent() {
        eventService.deleteEvent(lossEvent);
        verify(eventDao, times(1)).delete(lossEvent);
    }

    @Test
    public void testAddLoosing() {
        eventService.createEvent(findEvent);
        Assert.assertEquals(null, findEvent.getOwner());

        eventService.addLoosing(findEvent, user, Date.from(Instant.now()), "Brno");
        Assert.assertTrue(findEvent.getOwner() != null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddLoosingAlreadyLost() {
        eventService.createEvent(lossEvent);
        eventService.addLoosing(lossEvent, user, Date.from(Instant.now()), "Brno");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddLoosingNullOwner() {
        eventService.addLoosing(findEvent, null, Date.from(Instant.now()), "Brno");
    }

    @Test
    public void testAddFinding() {
        eventService.createEvent(lossEvent);
        Assert.assertEquals(null, lossEvent.getFinder());

        eventService.addFinding(lossEvent, user, Date.from(Instant.now()), "Brno");
        Assert.assertTrue(lossEvent.getFinder() != null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddFindingAlreadyFound() {
        eventService.addFinding(findEvent, user, Date.from(Instant.now()), "Brno");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddFindingNullFinder() {
        eventService.addFinding(findEvent, null, Date.from(Instant.now()), "Brno");
    }

    @Test
    public void testCheckEventResolved() {
        Assert.assertFalse(eventService.checkEventResolved(lossEvent));
        lossEvent.setFinder(user);
        lossEvent.setPlaceOfFind("Brno");
        lossEvent.setDateOfFind(Date.from(Instant.now()));
        Assert.assertTrue(eventService.checkEventResolved(lossEvent));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCheckEventResolvedNullEvent() {
        eventService.checkEventResolved(null);
    }

    @Test
    public void testFindEventById() {
        when(eventDao.findById(0)).thenReturn(lossEvent);

        Event returned = eventService.findEventById(lossEvent.getId());
        Assert.assertEquals(lossEvent, returned);

        verify(eventDao, times(1)).findById(0);
    }

    @Test
    public void testFindEventByItem() {
        when(eventDao.findByItem(lossEvent.getItem())).thenReturn(lossEvent);

        Event returned = eventService.findEventByItem(lossEvent.getItem());
        Assert.assertEquals(lossEvent, returned);

        verify(eventDao, times(1)).findByItem(lossEvent.getItem());
    }

    @Test
    public void testFindAllEvents() {
        events.add(lossEvent);
        events.add(findEvent);
        when(eventDao.findAll()).thenReturn(events);

        Assert.assertEquals(eventService.findAllEvents().size(), 2);
    }

    @Test
    public void testFindEventsByFinder() {
        events.add(findEvent);
        when(eventDao.findEventByFinder(findEvent.getFinder())).thenReturn(events);

        List<Event> res = eventService.findEventsByFinder(findEvent.getFinder());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(findEvent, res.get(0));
    }

    @Test
    public void testFindEventsByOwner() {
        events.add(lossEvent);
        when(eventDao.findEventByOwner(lossEvent.getOwner())).thenReturn(events);

        List<Event> res = eventService.findEventsByOwner(lossEvent.getOwner());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lossEvent, res.get(0));
    }

    @Test
    public void testFindEventsByPlaceOfLoss() {
        events.add(lossEvent);
        when(eventDao.findEventByPlaceOfLoss(lossEvent.getPlaceOfLoss())).thenReturn(events);

        List<Event> res = eventService.findEventsByPlaceOfLoss(lossEvent.getPlaceOfLoss());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lossEvent, res.get(0));
    }

    @Test
    public void testFindEventsByPlaceOfFind() {
        events.add(findEvent);
        when(eventDao.findEventByPlaceOfFind(findEvent.getPlaceOfFind())).thenReturn(events);

        List<Event> res = eventService.findEventsByPlaceOfFind(findEvent.getPlaceOfFind());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(findEvent, res.get(0));
    }

    @Test
    public void testFindEventsByDateOfFind() {
        events.add(findEvent);
        when(eventDao.findEventByDateOfFind(findEvent.getDateOfFind())).thenReturn(events);

        List<Event> res = eventService.findEventsByDateOfFind(findEvent.getDateOfFind());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(findEvent, res.get(0));
    }

    @Test
    public void testFindEventsByDateOfLoss() {
        events.add(lossEvent);
        when(eventDao.findEventByDateOfLoss(lossEvent.getDateOfLoss())).thenReturn(events);

        List<Event> res = eventService.findEventsByDateOfLoss(lossEvent.getDateOfLoss());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lossEvent, res.get(0));
    }

    @Test
    public void testFindEventsWithoutLoss() {
        events.add(findEvent);
        when(eventDao.findAll()).thenReturn(events);

        List<Event> res = eventService.findEventsWithoutLoss();
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(findEvent, res.get(0));
    }

    @Test
    public void testFindEventsWithoutFind() {
        events.add(lossEvent);
        when(eventDao.findAll()).thenReturn(events);

        List<Event> res = eventService.findEventsWithoutFind();
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lossEvent, res.get(0));
    }

    @Test
    public void getStatistics_loss_finds() {
        events.add(lossEvent);
        when(eventDao.findAll()).thenReturn(events);

        List<String> cities = new ArrayList<String>() {
            {
                add("Kladno");
                add("Most");
                add("Karviná");
                add("Brno");
                add("Frýdek-Místek");
                add("Karlove Vary");
                add("Jihlava");
            }
        };

        List<StatisticsDTO> res = eventService.getStatistics(cities, StatisticsType.LOSS);
        Assert.assertEquals(5, res.size());
    }
}
