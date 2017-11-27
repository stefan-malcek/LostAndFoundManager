package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.ServiceApplicationContext;
import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.entities.Event;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Adam Bananka
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
public class EventServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private EventDao eventDao;

    private Event event;

    @Autowired
    @InjectMocks
    private EventService eventService;

    @BeforeMethod
    public void createData() {

    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreateEvent() {

    }

    @Test
    public void testCreateEventNull() {

    }

    @Test
    public void testDeleteEvent() {

    }

    @Test
    public void testDeleteEventNull() {

    }

    @Test
    public void testAddLoosing() {

    }

    @Test
    public void testAddLoosingAlreadyLost() {

    }

    @Test
    public void testAddLoosingNullOwner() {

    }

    @Test
    public void testAddFinding() {

    }

    @Test
    public void testAddFindingAlreadyFound() {

    }

    @Test
    public void testAddFindingNullFinder() {

    }

    @Test
    public void testCheckEventResolved() {

    }

    @Test
    public void testCheckEventResolvedNullEvent() {

    }

    @Test
    public void testFindEventById() {

    }

    @Test
    public void testFindEventByIdZero() {

    }

    @Test
    public void testFindEventByIdNegative() {

    }

    @Test
    public void testFindEventByItem() {

    }

    @Test
    public void testFindEventByItemNull() {

    }

    @Test
    public void testFindAllEvents() {

    }

    @Test
    public void testFindEventsByFinder() {

    }

    @Test
    public void testFindEventsByFinderNull() {

    }

    @Test
    public void testFindEventsByOwner() {

    }

    @Test
    public void testFindEventsByOwnerNull() {

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
