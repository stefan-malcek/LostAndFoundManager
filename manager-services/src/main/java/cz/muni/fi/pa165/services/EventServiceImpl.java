package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.entities.User;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Adam Bananka
 */
public class EventServiceImpl implements EventService{

    @Inject
    private EventDao eventDao;

    @Override
    public void createEvent(Event event) {
        eventDao.create(event);
    }

    @Override
    public void deleteEvent(Event event) {
        eventDao.delete(event);
    }

    @Override
    public void addLoosing(Event event, User owner, LocalDate dateOfLoss, String placeOfLoss) {
        if (event.getOwner() != null) {
            throw new IllegalArgumentException("Loosing already added.");
        }
        event.setOwner(owner);
        event.setDateOfLoss(dateOfLoss);
        event.setPlaceOfLoss(placeOfLoss);
    }

    @Override
    public void addFinding(Event event, User finder, LocalDate dateOfFind, String placeOfFind) {
        if (event.getFinder() != null) {
            throw new IllegalArgumentException("Finding already added.");
        }
        event.setFinder(finder);
        event.setDateOfFind(dateOfFind);
        event.setPlaceOfFind(placeOfFind);
    }

    @Override
    public boolean checkEventResolved(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("event can't be null.");
        }
        return  event.getFinder() != null && event.getOwner() != null;
    }

    @Override
    public Event findEventById(long id) {
        return eventDao.findById(id);
    }

    @Override
    public Event findEventByItem(Item item) {
        return eventDao.findByItem(item);
    }

    @Override
    public List<Event> findAllEvents() {
        return eventDao.findAll();
    }

    @Override
    public List<Event> findEventsByFinder(User finder) {
        return eventDao.findEventByFinder(finder);
    }

    @Override
    public List<Event> findEventsByOwner(User owner) {
        return eventDao.findEventByOwner(owner);
    }

    @Override
    public List<Event> findEventsByPlaceOfLoss(String place) {
        return eventDao.findEventByPlaceOfLoss(place);
    }

    @Override
    public List<Event> findEventsByPlaceOfFind(String place) {
        return eventDao.findEventByPlaceOfFind(place);
    }

    @Override
    public List<Event> findEventsByDateOfFind(LocalDate date) {
        return eventDao.findEventByDateOfFind(date);
    }

    @Override
    public List<Event> findEventsByDateOfLoss(LocalDate date) {
        return eventDao.findEventByDateOfLoss(date);
    }
}
