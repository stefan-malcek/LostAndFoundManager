package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.entities.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author Adam Bananka
 */
public interface EventService {

    /**
     * Creates new event.
     *
     * @param event event to create
     */
    void createEvent(Event event);

    /**
     * Deletes given event.
     *
     * @param event event to delete
     */
    void deleteEvent(Event event);

    /**
     * Adds loosing to an existing event.
     *
     * @param event existing event to update
     * @param owner owner of event's item
     * @param dateOfLoss    date of loosing
     * @param placeOfLoss   place of loosing
     */
    void addLoosing(Event event, User owner, Date dateOfLoss, String placeOfLoss);

    /**
     * Ads finding to an existing event.
     *
     * @param event existing event to update
     * @param finder    finder of event's item
     * @param dateOfFind    date of finding
     * @param placeOfFind   place of finding
     */
    void addFinding(Event event, User finder, Date dateOfFind, String placeOfFind);

    /**
     * Checks whether event have reported both loosing and finding.
     *
     * @param event event to check
     * @return  true when event is resolved, false otherwise
     */
    boolean checkEventResolved(Event event);

    /**
     * Finds event with given id.
     *
     * @param id    id of desired event
     * @return  desired event
     */
    Event findEventById(long id);

    /**
     * Finds event with given item.
     *
     * @param item  item of desired event
     * @return  desired event
     */
    Event findEventByItem(Item item);

    /**
     * Finds all events.
     *
     * @return  list of all events
     */
    List<Event> findAllEvents();

    /**
     * Finds events with given finder.
     *
     * @param finder    finder of desired events
     * @return  list of desired events
     */
    List<Event> findEventsByFinder(User finder);

    /**
     *  Finds events with given owner.
     *
     * @param owner    owner of desired events
     * @return  list of desired events
     */
    List<Event> findEventsByOwner(User owner);

    /**
     * Finds events with given place of loss.
     *
     * @param place place of loss of desired events
     * @return  list of desired events
     */
    List<Event> findEventsByPlaceOfLoss(String place);

    /**
     * Finds events with given place of find.
     *
     * @param place place of find of desired events
     * @return  list of desired events
     */
    List<Event> findEventsByPlaceOfFind(String place);

    /**
     * Finds events with given date of find.
     *
     * @param date  date of find of desired events
     * @return  list of desired events
     */
    List<Event> findEventsByDateOfFind(Date date);

    /**
     * Finds events with given date of loss.
     *
     * @param date  date of loss of desired events
     * @return  list of desired events
     */
    List<Event> findEventsByDateOfLoss(Date date);
    
    /**
     * Finds events without report about loss.
     *
     * @return  list of desired events
     */
    List<Event> findEventsWithoutLoss();
    
     /**
     * Finds events without report about find.
     *
     * @return  list of desired events
     */
    List<Event> findEventsWithoutFind();
}
