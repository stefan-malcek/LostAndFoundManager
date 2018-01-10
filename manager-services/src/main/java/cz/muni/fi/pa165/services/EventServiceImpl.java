package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.StatisticsComparator;
import cz.muni.fi.pa165.dao.EventDao;
import cz.muni.fi.pa165.dto.StatisticsDTO;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.enums.StatisticsType;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Adam Bananka
 */
@Service
public class EventServiceImpl implements EventService {

    private static final int STATISTIC_ENTRIES = 4;

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
    public void addLoosing(Event event, User owner, Date dateOfLoss, String placeOfLoss) {
        if (event.getOwner() != null) {
            throw new IllegalArgumentException("Loosing already added.");
        }
        if (owner == null || dateOfLoss == null || placeOfLoss.isEmpty()) {
            throw new IllegalArgumentException("Wrong arguments.");
        }
        event.setOwner(owner);
        event.setDateOfLoss(dateOfLoss);
        event.setPlaceOfLoss(placeOfLoss);
    }

    @Override
    public void addFinding(Event event, User finder, Date dateOfFind, String placeOfFind) {
        if (event.getFinder() != null) {
            throw new IllegalArgumentException("Finding already added.");
        }
        if (finder == null || dateOfFind == null || placeOfFind.isEmpty()) {
            throw new IllegalArgumentException("Wrong arguments.");
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
        return event.getFinder() != null && event.getOwner() != null;
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
    public List<Event> findEventsByDateOfFind(Date date) {
        return eventDao.findEventByDateOfFind(date);
    }

    @Override
    public List<Event> findEventsByDateOfLoss(Date date) {
        return eventDao.findEventByDateOfLoss(date);
    }

    @Override
    public List<Event> findEventsWithoutLoss() {
        List<Event> events = eventDao.findAll();
        List<Event> returnedEvents = new ArrayList<>();

        for (Event event : events) {
            if (event.getPlaceOfLoss() == null) {
                returnedEvents.add(event);
            }
        }
        return returnedEvents;
    }

    @Override
    public List<Event> findEventsWithoutFind() {
        List<Event> events = eventDao.findAll();
        List<Event> returnedEvents = new ArrayList<>();

        for (Event event : events) {
            if (event.getPlaceOfFind() == null) {
                returnedEvents.add(event);
            }
        }

        return returnedEvents;
    }

    @Override
    public List<StatisticsDTO> getStatistics(List<String> cities,
            StatisticsType type) {
        List<Event> events = eventDao.findAll();
        List<String> addresses = getAddresses(events, type);
        List<StatisticsDTO> statistics = computeStatistics(cities, addresses);
        
        // Sort.
        Collections.sort(statistics, new StatisticsComparator());

        // Take first 4 records and the rest sum together as Others.
        List<StatisticsDTO> list = new ArrayList<>();
        list.addAll(statistics.subList(0, STATISTIC_ENTRIES));
        int other = 0;
        for (int i = STATISTIC_ENTRIES; i < cities.size(); i++) {
            other += statistics.get(i).getTimes();
        }
        list.add(new StatisticsDTO("Other", other));
        Collections.sort(list, new StatisticsComparator());

        return list;
    }

    private List<String> getAddresses(List<Event> events, StatisticsType type) {
        List<String> addresses = new ArrayList<>();
        for (Event event : events) {
            if (type == StatisticsType.LOSS) {
                if (event.getPlaceOfLoss() != null) {
                    addresses.add(event.getPlaceOfLoss());
                }
            } else if (type == StatisticsType.FIND) {
                if (event.getDateOfFind() != null) {
                    addresses.add(event.getPlaceOfFind());
                }
            }
        }
        return addresses;
    }

    private List<StatisticsDTO> computeStatistics(List<String> cities,
            List<String> addresses) {
        List<StatisticsDTO> statistics = new ArrayList<>();
        for (String city : cities) {
            int times = 0;
            for (String address : addresses) {
                if (address.contains(city)) {
                    times++;
                }
            }
            statistics.add(new StatisticsDTO(city, times));
        }
        return statistics;

    }
}
