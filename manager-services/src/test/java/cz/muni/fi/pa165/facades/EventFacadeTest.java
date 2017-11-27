package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.enums.ItemColor;
import cz.muni.fi.pa165.enums.UserRole;
import cz.muni.fi.pa165.facade.CategoryFacade;
import cz.muni.fi.pa165.facade.EventFacade;
import cz.muni.fi.pa165.facade.ItemFacade;
import cz.muni.fi.pa165.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Adam Bananka
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class EventFacadeTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private EventFacade eventFacade;
    @Autowired
    private ItemFacade itemFacade;
    @Autowired
    private CategoryFacade categoryFacade;
    @Autowired
    private UserFacade userFacade;

    private EventDTO lossEvent;
    private EventDTO findEvent;

    @BeforeMethod
    public void setup() {
        UserDTO user = new UserDTO();
        user.setName("Tester");
        user.setEmail("mail@test.com");
        user.setUserRole(UserRole.MEMBER);
        userFacade.register(user, "psw12345");
        CategoryCreateDTO categoryDTO = new CategoryCreateDTO();
        categoryDTO.setName("Electronics");
        categoryDTO.setDescription("electro stuff");
        long id = categoryFacade.createCategory(categoryDTO);
        CategoryDTO category = categoryFacade.getCategoryById(id);
        ItemDTO laptop = new ItemDTO();
        laptop.setColor(ItemColor.GRAY);
        laptop.setName("Laptop");
        laptop.setDescription("Lenovo laptop");
        laptop.setCategory(category);
        itemFacade.create(laptop);
        ItemDTO mobile = new ItemDTO();
        mobile.setName("Mobile");
        mobile.setColor(ItemColor.WHITE);
        mobile.setDescription("Samsung");
        mobile.setCategory(category);
        itemFacade.create(mobile);

        lossEvent = new EventDTO();
        lossEvent.setItem(laptop);
        lossEvent.setOwner(user);
        lossEvent.setDateOfLoss(LocalDate.now().minusDays(2));
        lossEvent.setPlaceOfLoss("Brno");
        findEvent = new EventDTO();
        findEvent.setItem(mobile);
        findEvent.setFinder(user);
        findEvent.setDateOfFind(LocalDate.now().minusDays(1));
        findEvent.setPlaceOfFind("Brno");
    }

    @Test
    public void testCreateEvent() {
        Assert.assertEquals(0, lossEvent.getId());
        eventFacade.createEvent(lossEvent);
        Assert.assertTrue(lossEvent.getId() != 0);
    }

    @Test
    public void testDeleteEvent() {
        eventFacade.createEvent(lossEvent);
        eventFacade.createEvent(findEvent);

        Assert.assertEquals(eventFacade.findAllEvents().size(), 2);
        eventFacade.deleteEvent(findEvent.getId());

        Assert.assertEquals(eventFacade.findAllEvents().size(), 1);
        Assert.assertEquals(eventFacade.findAllEvents().get(0), lossEvent);
    }

    @Test
    public void testAddLoosing() {
        eventFacade.createEvent(findEvent);
        EventLossDTO loss = new EventLossDTO();
        loss.setId(findEvent.getId());
        loss.setItem(findEvent.getItem());
        loss.setPlaceOfLoss("brno");
        loss.setDateOfLoss(LocalDate.now().minusDays(3));
        loss.setOwner(findEvent.getFinder());
        Assert.assertEquals(findEvent.getId(), eventFacade.addLoosing(loss));
        EventDTO updated = eventFacade.findEventById(findEvent.getId());
        Assert.assertEquals(updated.getFinder(), findEvent.getFinder());
    }

    @Test
    public void testAddLoosingNotCreated() {
        EventLossDTO loss = new EventLossDTO();
        loss.setItem(lossEvent.getItem());
        loss.setOwner(lossEvent.getOwner());
        loss.setDateOfLoss(lossEvent.getDateOfLoss());
        loss.setPlaceOfLoss(lossEvent.getPlaceOfLoss());
        long id = eventFacade.addLoosing(loss);
        Assert.assertEquals(lossEvent, eventFacade.findEventById(id));
    }

    @Test
    public void testAddFinding() {
        eventFacade.createEvent(lossEvent);
        EventFindDTO find = new EventFindDTO();
        find.setId(lossEvent.getId());
        find.setItem(lossEvent.getItem());
        find.setDateOfFind(LocalDate.now());
        find.setPlaceOfFind("Brno");
        find.setFinder(lossEvent.getOwner());
        Assert.assertEquals(lossEvent.getId(), eventFacade.addFinding(find));
        EventDTO updated = eventFacade.findEventById(lossEvent.getId());
        Assert.assertEquals(updated.getOwner(), lossEvent.getOwner());
    }

    @Test
    public void testAddFindingNotCreated() {
        EventFindDTO find = new EventFindDTO();
        find.setItem(findEvent.getItem());
        find.setDateOfFind(findEvent.getDateOfFind());
        find.setPlaceOfFind(findEvent.getPlaceOfFind());
        find.setFinder(findEvent.getFinder());
        long id = eventFacade.addFinding(find);
        Assert.assertEquals(findEvent, eventFacade.findEventById(id));
    }

    @Test
    public void testCheckEventResolved() {
        eventFacade.createEvent(lossEvent);
        Assert.assertFalse(eventFacade.checkEventResolved(lossEvent.getId()));
        EventFindDTO find = new EventFindDTO();
        find.setId(lossEvent.getId());
        find.setItem(lossEvent.getItem());
        find.setDateOfFind(LocalDate.now());
        find.setPlaceOfFind("Brno");
        find.setFinder(lossEvent.getOwner());
        eventFacade.addFinding(find);
        Assert.assertTrue(eventFacade.checkEventResolved(lossEvent.getId()));
    }

    @Test
    public void testFindEventById() {
        eventFacade.createEvent(lossEvent);
        eventFacade.createEvent(findEvent);
        Assert.assertEquals(lossEvent, eventFacade.findEventById(lossEvent.getId()));
    }

    @Test
    public void testFindEventByItem() {
        eventFacade.createEvent(lossEvent);
        eventFacade.createEvent(findEvent);
        Assert.assertEquals(lossEvent, eventFacade.findEventByItem(lossEvent.getItem().getId()));
    }

    @Test
    public void testFindAllEvents() {
        eventFacade.createEvent(lossEvent);
        eventFacade.createEvent(findEvent);
        Assert.assertEquals(eventFacade.findAllEvents().size(), 2);
    }

    @Test
    public void testFindEventsByFinder() {
        eventFacade.createEvent(lossEvent);
        eventFacade.createEvent(findEvent);
        List<EventDTO> res = eventFacade.findEventsByFinder(findEvent.getFinder().getId());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(findEvent, res.get(0));
    }

    @Test
    public void testFindEventsByOwner() {
        eventFacade.createEvent(lossEvent);
        eventFacade.createEvent(findEvent);
        List<EventDTO> res = eventFacade.findEventsByOwner(lossEvent.getOwner().getId());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lossEvent, res.get(0));
    }

    @Test
    public void testFindEventsByPlaceOfLoss() {
        eventFacade.createEvent(lossEvent);
        eventFacade.createEvent(findEvent);
        List<EventDTO> res = eventFacade.findEventsByPlaceOfLoss(lossEvent.getPlaceOfLoss());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lossEvent, res.get(0));
    }

    @Test
    public void testFindEventsByPlaceOfFind() {
        eventFacade.createEvent(lossEvent);
        eventFacade.createEvent(findEvent);
        List<EventDTO> res = eventFacade.findEventsByPlaceOfFind(findEvent.getPlaceOfFind());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(findEvent, res.get(0));
    }

    @Test
    public void testFindEventsByDateOfFind() {
        eventFacade.createEvent(lossEvent);
        eventFacade.createEvent(findEvent);
        List<EventDTO> res = eventFacade.findEventsByDateOfFind(findEvent.getDateOfFind());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(findEvent, res.get(0));
    }

    @Test
    public void testFindEventsByDateOfLoss() {
        eventFacade.createEvent(lossEvent);
        eventFacade.createEvent(findEvent);
        List<EventDTO> res = eventFacade.findEventsByDateOfLoss(lossEvent.getDateOfLoss());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lossEvent, res.get(0));
    }
}
