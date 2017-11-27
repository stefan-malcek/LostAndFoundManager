package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.facade.EventFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Adam Bananka
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class EventFacadeTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private EventFacade eventFacade;

    private EventDTO lossEvent;
    private EventDTO findEvent;

    @BeforeMethod
    public void setup() {

    }

    @Test
    public void testCreateEvent() {

    }

    @Test
    public void testDeleteEvent() {

    }

    @Test
    public void testAddLoosing() {

    }

    @Test
    public void testAddFinding() {

    }

    @Test
    public void testCheckEventResolved() {

    }

    @Test
    public void testFindEventById() {

    }

    @Test
    public void testFindEventByItem() {

    }

    @Test
    public void testFindAllEvents() {

    }

    @Test
    public void testFindEventsByFinder() {

    }

    @Test
    public void testFindEventsByOwner() {

    }

    @Test
    public void testFindEventsByPlaceOfLoss() {

    }

    @Test
    public void testFindEventsByPlaceOfFind() {

    }

    @Test
    public void testFindEventsByDateOfFind() {

    }

    @Test
    public void testFindEventsByDateOfLoss() {

    }
}
