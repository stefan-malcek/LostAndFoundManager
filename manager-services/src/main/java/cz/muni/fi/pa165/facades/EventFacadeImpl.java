package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.EventFindDTO;
import cz.muni.fi.pa165.dto.EventLossDTO;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.facade.EventFacade;
import cz.muni.fi.pa165.services.EventService;
import cz.muni.fi.pa165.services.MappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Adam Bananka
 */
@Service
@Transactional
public class EventFacadeImpl implements EventFacade {

    @Inject
    private EventService eventService;

    @Inject
    private ItemService itemService;

    @Inject
    private UserService userService;

    @Inject
    private MappingService mappingService;


    @Override
    public long createEvent(EventDTO eventDTO) {
        Event event = mappingService.mapTo(eventDTO, Event.class);
        eventService.createEvent(event);
        return event.getId();
    }

    @Override
    public void deleteEvent(long eventId) {
        Event event = new Event();
        event.setId(eventId);
        eventService.deleteEvent(event);
    }

    @Override
    public long addLoosing(EventLossDTO eventDTO) {
        Event event = eventService.findEventById(eventDTO.getId());
        if (event == null) {
            event = mappingService.mapTo(eventDTO, Event.class);
            eventService.createEvent(event);
            return event.getId();
        }
        User owner = mappingService.mapTo(eventDTO.getOwner(), User.class);
        eventService.addLoosing(event, owner, eventDTO.getDateOfLoss(), eventDTO.getPlaceOfLoss());
        return event.getId();
    }

    @Override
    public long addFinding(EventFindDTO eventDTO) {
        Event event = eventService.findEventById(eventDTO.getId());
        if (event == null) {
            event = mappingService.mapTo(eventDTO, Event.class);
            eventService.createEvent(event);
            return event.getId();
        }
        User finder = mappingService.mapTo(eventDTO.getFinder(), User.class);
        eventService.addLoosing(event, finder, eventDTO.getDateOfFind(), eventDTO.getPlaceOfFind());
        return event.getId();
    }

    @Override
    public boolean checkEventResolved(long eventId) {
        Event event = eventService.findEventById(eventId);
        return eventService.checkEventResolved(event);
    }

    @Override
    public EventDTO findEventById(long eventId) {
        Event event = eventService.findEventById(eventId);
        return event == null
                ? null
                : mappingService.mapTo(event, EventDTO.class);
    }

    @Override
    public EventDTO findEventByItem(long itemId) {
        ItemDTO itemDTO = itemService.findById(itemId);
        if (itemDTO == null) {
            return null;
        }
        Item item = mappingService.mapTo(itemDTO, Item.class);
        Event event = eventService.findEventByItem(item);
        return event == null
                ? null
                : mappingService.mapTo(event, EventDTO.class);
    }

    @Override
    public List<EventDTO> findAllEvents() {
        List<Event> events = eventService.findAllEvents();
        return mappingService.mapTo(events, EventDTO.class);
    }

    @Override
    public List<EventDTO> findEventsByFinder(long finderId) {
        UserDTO userDTO = userService.findUserById(finderId);
        if (userDTO == null) {
            return null;
        }
        User finder = mappingService.mapTo(userDTO, User.class);
        List<Event> events = eventService.findEventsByFinder(finder);
        return mappingService.mapTo(events, EventDTO.class);
    }

    @Override
    public List<EventDTO> findEventsByOwner(long ownerId) {
        UserDTO userDTO = userService.findUserById(ownerId);
        if (userDTO == null) {
            return null;
        }
        User owner = mappingService.mapTo(userDTO, User.class);
        List<Event> events = eventService.findEventsByOwner(owner);
        return mappingService.mapTo(events, EventDTO.class);
    }

    @Override
    public List<EventDTO> findEventsByPlaceOfLoss(String place) {
        List<Event> events = eventService.findEventsByPlaceOfLoss(place);
        return mappingService.mapTo(events, EventDTO.class);
    }

    @Override
    public List<EventDTO> findEventsByPlaceOfFind(String place) {
        List<Event> events = eventService.findEventsByPlaceOfFind(place);
        return mappingService.mapTo(events, EventDTO.class);
    }

    @Override
    public List<EventDTO> findEventsByDateOfFind(LocalDate date) {
        List<Event> events = eventService.findEventsByDateOfFind(date);
        return mappingService.mapTo(events, EventDTO.class);
    }

    @Override
    public List<EventDTO> findEventsByDateOfLoss(LocalDate date) {
        List<Event> events = eventService.findEventsByDateOfLoss(date);
        return mappingService.mapTo(events, EventDTO.class);
    }
}
