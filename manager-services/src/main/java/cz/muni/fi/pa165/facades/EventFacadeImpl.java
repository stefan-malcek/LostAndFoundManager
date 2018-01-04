package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.dto.EventCreateDTO;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.EventFindDTO;
import cz.muni.fi.pa165.dto.EventLossDTO;
import cz.muni.fi.pa165.dto.ItemDTO;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.facade.EventFacade;
import cz.muni.fi.pa165.services.EventService;
import cz.muni.fi.pa165.services.ItemService;
import cz.muni.fi.pa165.services.MappingService;
import cz.muni.fi.pa165.services.UserService;
import exceptions.LostAndFoundManagerDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Date;
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
    public long createEvent(EventCreateDTO eventDTO) {
        try {
            Event event = mappingService.mapTo(eventDTO, Event.class);
            eventService.createEvent(event);
            return event.getId();
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Event cannot be created.", ex);
        }
    }

    @Override
    public void deleteEvent(long eventId) {
        try {
            Event event = eventService.findEventById(eventId);
            eventService.deleteEvent(event);
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Event cannot be deleted.", ex);
        }
    }

    @Override
    public long addLoosing(EventLossDTO eventDTO) {
        try {
            if (eventDTO.getId() <= 0) {
                Event event = mappingService.mapTo(eventDTO, Event.class);
                eventService.createEvent(event);
                return event.getId();
            }

            Event event = eventService.findEventById(eventDTO.getId());
            User owner = mappingService.mapTo(eventDTO.getOwner(), User.class);
            eventService.addLoosing(event, owner, eventDTO.getDateOfLoss(), eventDTO.getPlaceOfLoss());
            return event.getId();
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Loosing cannot be added to event.", ex);
        }
    }

    @Override
    public long addFinding(EventFindDTO eventDTO) {
        try {
            if (eventDTO.getId() <= 0) {
                Event event = mappingService.mapTo(eventDTO, Event.class);
                eventService.createEvent(event);
                return event.getId();
            }

            Event event = eventService.findEventById(eventDTO.getId());
            User finder = mappingService.mapTo(eventDTO.getFinder(), User.class);
            eventService.addFinding(event, finder, eventDTO.getDateOfFind(), eventDTO.getPlaceOfFind());
            return event.getId();
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Finding cannot be added to event.", ex);
        }
    }

    @Override
    public boolean checkEventResolved(long eventId) {
        try {
            Event event = eventService.findEventById(eventId);
            return eventService.checkEventResolved(event);
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Event cannot be checked.", ex);
        }
    }

    @Override
    public EventDTO findEventById(long eventId) {
        try {
            Event event = eventService.findEventById(eventId);
            return event == null
                    ? null
                    : mappingService.mapTo(event, EventDTO.class);
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Cannot find event by id.", ex);
        }
    }

    @Override
    public EventDTO findEventByItem(long itemId) {
        try {
            Item item = itemService.findById(itemId);
            if (item == null) {
                return null;
            }
            Event event = eventService.findEventByItem(item);
            return event == null
                    ? null
                    : mappingService.mapTo(event, EventDTO.class);
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Cannot find event by item.", ex);
        }
    }

    @Override
    public List<EventDTO> findAllEvents() {
        try {
            List<Event> events = eventService.findAllEvents();
            return mappingService.mapTo(events, EventDTO.class);
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Cannot find all events.", ex);
        }
    }

    @Override
    public List<EventDTO> findEventsByFinder(long finderId) {
        try {
            User user = userService.findUserById(finderId);
            if (user == null) {
                return null;
            }
            List<Event> events = eventService.findEventsByFinder(user);
            return mappingService.mapTo(events, EventDTO.class);
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Cannot find events by finder.", ex);
        }
    }

    @Override
    public List<EventDTO> findEventsByOwner(long ownerId) {
        try {
            User user = userService.findUserById(ownerId);
            if (user == null) {
                return null;
            }
            List<Event> events = eventService.findEventsByOwner(user);
            return mappingService.mapTo(events, EventDTO.class);
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Cannot find events by owner.", ex);
        }
    }

    @Override
    public List<EventDTO> findEventsByPlaceOfLoss(String place) {
        try {
            List<Event> events = eventService.findEventsByPlaceOfLoss(place);
            return mappingService.mapTo(events, EventDTO.class);
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Cannot find events by place of loss.", ex);
        }
    }

    @Override
    public List<EventDTO> findEventsByPlaceOfFind(String place) {
        try {
            List<Event> events = eventService.findEventsByPlaceOfFind(place);
            return mappingService.mapTo(events, EventDTO.class);
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Cannot find events by place of find.", ex);
        }
    }

    @Override
    public List<EventDTO> findEventsByDateOfFind(Date date) {
        try {
            List<Event> events = eventService.findEventsByDateOfFind(date);
            return mappingService.mapTo(events, EventDTO.class);
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Cannot find events by date of find.", ex);
        }
    }

    @Override
    public List<EventDTO> findEventsByDateOfLoss(Date date) {
        try {
            List<Event> events = eventService.findEventsByDateOfLoss(date);
            return mappingService.mapTo(events, EventDTO.class);
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Cannot find events by date of loss.", ex);
        }
    }

    @Override
    public List<EventDTO> findEventsWithoutLoss() {
        try {
            List<Event> events = eventService.findEventsWithoutLoss();
            return mappingService.mapTo(events, EventDTO.class);
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Cannot find events without loss.", ex);
        }
    }

    @Override
    public List<EventDTO> findEventsWithoutFind() {
        try {
            List<Event> events = eventService.findEventsWithoutFind();
            return mappingService.mapTo(events, EventDTO.class);
        } catch (Exception ex) {
            throw new LostAndFoundManagerDataAccessException("Cannot find events without find.", ex);
        }
    }
}
