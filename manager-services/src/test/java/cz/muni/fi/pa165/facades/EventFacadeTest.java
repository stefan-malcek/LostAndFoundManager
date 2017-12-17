package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.enums.ItemColor;
import cz.muni.fi.pa165.enums.UserRole;
import cz.muni.fi.pa165.facade.CategoryFacade;
import cz.muni.fi.pa165.facade.EventFacade;
import cz.muni.fi.pa165.facade.ItemFacade;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.services.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * @author Adam Bananka
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class EventFacadeTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private EventFacade eventFacade;
    @Autowired
    private ItemFacade itemFacade;
    @Autowired
    private CategoryFacade categoryFacade;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private MappingService mappingService;

    private EventDTO lossEvent;
    private EventDTO findEvent;

    @BeforeMethod
    public void setup() {
        UserDTO user = new UserDTO();
        user.setName("Tester");
        user.setEmail("mail@test.com");
        user.setUserRole(UserRole.MEMBER);
        userFacade.register(user, "psw12345");
        user = userFacade.findAllUsers().get(0);
        CategoryCreateDTO categoryDTO = new CategoryCreateDTO();
        categoryDTO.setName("Electronics");
        categoryDTO.setDescription("electro stuff");
        categoryFacade.createCategory(categoryDTO);
        CategoryDTO category = categoryFacade.getAllCategories().get(0);
        ItemCreateDTO laptopCreateDto = new ItemCreateDTO();
        laptopCreateDto.setColor(ItemColor.GRAY);
        laptopCreateDto.setName("Laptop");
        laptopCreateDto.setDescription("Lenovo laptop");
        laptopCreateDto.setCategory(category);
        itemFacade.create(laptopCreateDto);
        ItemCreateDTO mobileCreateDto = new ItemCreateDTO();
        mobileCreateDto.setName("Mobile");
        mobileCreateDto.setColor(ItemColor.WHITE);
        mobileCreateDto.setDescription("Samsung");
        mobileCreateDto.setCategory(category);
        itemFacade.create(mobileCreateDto);
        ItemDTO laptop = itemFacade.getAllItems().get(0);
        ItemDTO mobile = itemFacade.getAllItems().get(1);

        EventCreateDTO lossEventCreate = new EventCreateDTO();
        lossEventCreate.setItem(laptop);
        lossEventCreate.setOwner(user);
        lossEventCreate.setDateOfLoss(Date.from(Instant.now()));
        lossEventCreate.setPlaceOfLoss("Brno");
        EventCreateDTO findEventCreate = new EventCreateDTO();
        findEventCreate.setItem(mobile);
        findEventCreate.setFinder(user);
        findEventCreate.setDateOfFind(Date.from(Instant.now()));
        findEventCreate.setPlaceOfFind("Brno");

        eventFacade.createEvent(lossEventCreate);
        lossEvent = eventFacade.findAllEvents().get(0);
        eventFacade.createEvent(findEventCreate);
        findEvent = eventFacade.findAllEvents().get(1);
    }

    @Test
    public void testDeleteEvent() {
        Assert.assertEquals(eventFacade.findAllEvents().size(), 2);
        eventFacade.deleteEvent(findEvent.getId());

        Assert.assertEquals(eventFacade.findAllEvents().size(), 1);
        Assert.assertEquals(eventFacade.findAllEvents().get(0), lossEvent);
    }

    @Test
    public void testAddLoosing() {
        EventLossDTO loss = new EventLossDTO();
        loss.setId(findEvent.getId());
        loss.setItem(findEvent.getItem());
        loss.setPlaceOfLoss("brno");
        loss.setDateOfLoss(Date.from(Instant.now()));
        loss.setOwner(findEvent.getFinder());
        Assert.assertEquals(findEvent.getId(), eventFacade.addLoosing(loss));
        EventDTO updated = eventFacade.findEventById(findEvent.getId());
        Assert.assertEquals(updated.getFinder(), findEvent.getFinder());
    }

    @Test
    public void testAddLoosingNotCreated() {
        ItemCreateDTO tabCreateDto = new ItemCreateDTO();
        tabCreateDto.setColor(ItemColor.GRAY);
        tabCreateDto.setName("Tablet");
        tabCreateDto.setDescription("iPad");
        tabCreateDto.setCategory(lossEvent.getItem().getCategory());
        long tabId = itemFacade.create(tabCreateDto);
        ItemDTO tab = itemFacade.findById(tabId);

        EventLossDTO loss = new EventLossDTO();
        loss.setItem(tab);
        loss.setOwner(lossEvent.getOwner());
        loss.setDateOfLoss(lossEvent.getDateOfLoss());
        loss.setPlaceOfLoss(lossEvent.getPlaceOfLoss());
        long id = eventFacade.addLoosing(loss);

        EventDTO event = mappingService.mapTo(loss, EventDTO.class);
        Assert.assertEquals(event, eventFacade.findEventById(id));
    }

    @Test
    public void testAddFinding() {
        EventFindDTO find = new EventFindDTO();
        find.setId(lossEvent.getId());
        find.setItem(lossEvent.getItem());
        find.setDateOfFind(Date.from(Instant.now()));
        find.setPlaceOfFind("Brno");
        find.setFinder(lossEvent.getOwner());
        Assert.assertEquals(lossEvent.getId(), eventFacade.addFinding(find));
        EventDTO updated = eventFacade.findEventById(lossEvent.getId());
        Assert.assertEquals(updated.getOwner(), lossEvent.getOwner());
    }

    @Test
    public void testAddFindingNotCreated() {
        ItemCreateDTO tabCreateDto = new ItemCreateDTO();
        tabCreateDto.setColor(ItemColor.GRAY);
        tabCreateDto.setName("Tablet");
        tabCreateDto.setDescription("iPad");
        tabCreateDto.setCategory(lossEvent.getItem().getCategory());
        long tabId = itemFacade.create(tabCreateDto);
        ItemDTO tab = itemFacade.findById(tabId);

        EventFindDTO find = new EventFindDTO();
        find.setItem(tab);
        find.setDateOfFind(findEvent.getDateOfFind());
        find.setPlaceOfFind(findEvent.getPlaceOfFind());
        find.setFinder(findEvent.getFinder());
        long id = eventFacade.addFinding(find);

        EventDTO event = mappingService.mapTo(find, EventDTO.class);
        Assert.assertEquals(event, eventFacade.findEventById(id));
    }

    @Test
    public void testCheckEventResolved() {
        Assert.assertFalse(eventFacade.checkEventResolved(lossEvent.getId()));
        EventFindDTO find = new EventFindDTO();
        find.setId(lossEvent.getId());
        find.setItem(lossEvent.getItem());
        find.setDateOfFind(Date.from(Instant.now()));
        find.setPlaceOfFind("Brno");
        find.setFinder(lossEvent.getOwner());
        eventFacade.addFinding(find);
        Assert.assertTrue(eventFacade.checkEventResolved(lossEvent.getId()));
    }

    @Test
    public void testFindEventById() {
        Assert.assertEquals(lossEvent, eventFacade.findEventById(lossEvent.getId()));
    }

    @Test
    public void testFindEventByItem() {
        Assert.assertEquals(lossEvent, eventFacade.findEventByItem(lossEvent.getItem().getId()));
    }

    @Test
    public void testFindAllEvents() {
        Assert.assertEquals(eventFacade.findAllEvents().size(), 2);
    }

    @Test
    public void testFindEventsByFinder() {
        List<EventDTO> res = eventFacade.findEventsByFinder(findEvent.getFinder().getId());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(findEvent, res.get(0));
    }

    @Test
    public void testFindEventsByOwner() {
        List<EventDTO> res = eventFacade.findEventsByOwner(lossEvent.getOwner().getId());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lossEvent, res.get(0));
    }

    @Test
    public void testFindEventsByPlaceOfLoss() {
        List<EventDTO> res = eventFacade.findEventsByPlaceOfLoss(lossEvent.getPlaceOfLoss());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lossEvent, res.get(0));
    }

    @Test
    public void testFindEventsByPlaceOfFind() {
        List<EventDTO> res = eventFacade.findEventsByPlaceOfFind(findEvent.getPlaceOfFind());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(findEvent, res.get(0));
    }

    @Test
    public void testFindEventsByDateOfFind() {
        List<EventDTO> res = eventFacade.findEventsByDateOfFind(findEvent.getDateOfFind());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(findEvent, res.get(0));
    }

    @Test
    public void testFindEventsByDateOfLoss() {
        List<EventDTO> res = eventFacade.findEventsByDateOfLoss(lossEvent.getDateOfLoss());
        Assert.assertEquals(1, res.size());
        Assert.assertEquals(lossEvent, res.get(0));
    }
}
