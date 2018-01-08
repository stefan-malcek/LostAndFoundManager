package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.EventCreateDTO;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.EventFindDTO;
import cz.muni.fi.pa165.dto.EventLossDTO;
import cz.muni.fi.pa165.dto.StatisticsDTO;
import cz.muni.fi.pa165.enums.StatisticsType;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author Adam Bananka
 */
public interface EventFacade {

    /**
     * Creates new event.
     *
     * @param eventDTO event to create
     * @return id of new event
     */
    long createEvent(EventCreateDTO eventDTO);

    /**
     * Deletes event with given id.
     *
     * @param eventId id of event to delete
     */
    void deleteEvent(long eventId);

    /**
     * Adds loosing to an existing event. If Event doesn't exists, creates new
     * one.
     *
     * @param eventLossDTO event loosing part to be added
     * @return id of updated or new event
     */
    long addLoosing(EventLossDTO eventLossDTO);

    /**
     * Ads finding to an existing event. If Event doesn't exists, creates new
     * one.
     *
     * @param eventFindDTO event finding part to be added
     * @return id of updated or new event
     */
    long addFinding(EventFindDTO eventFindDTO);

    /**
     * Checks whether event have reported both loosing and finding.
     *
     * @param eventId id of event to check
     * @return true when event is resolved, false otherwise
     */
    boolean checkEventResolved(long eventId);

    /**
     * Finds event with given id.
     *
     * @param eventId id of desired event
     * @return desired event
     */
    EventDTO findEventById(long eventId);

    /**
     * Finds event with given item.
     *
     * @param itemId id of item of desired event
     * @return desired event
     */
    EventDTO findEventByItem(long itemId);

    /**
     * Finds all events.
     *
     * @return list of all events
     */
    List<EventDTO> findAllEvents();

    /**
     * Finds events with given finder.
     *
     * @param finderId id of finder of desired events
     * @return list of desired events
     */
    List<EventDTO> findEventsByFinder(long finderId);

    /**
     * Finds events with given owner.
     *
     * @param ownerId if od owner of desired events
     * @return list of desired events
     */
    List<EventDTO> findEventsByOwner(long ownerId);

    /**
     * Finds events with given place of loss.
     *
     * @param place place of loss of desired events
     * @return list of desired events
     */
    List<EventDTO> findEventsByPlaceOfLoss(String place);

    /**
     * Finds events with given place of find.
     *
     * @param place place of find of desired events
     * @return list of desired events
     */
    List<EventDTO> findEventsByPlaceOfFind(String place);

    /**
     * Finds events with given date of find.
     *
     * @param date date of find of desired events
     * @return list of desired events
     */
    List<EventDTO> findEventsByDateOfFind(Date date);

    /**
     * Finds events with given date of loss.
     *
     * @param date date of loss of desired events
     * @return list of desired events
     */
    List<EventDTO> findEventsByDateOfLoss(Date date);

    /**
     * Finds events without dreport about loss
     *
     * @return list of desired events
     */
    List<EventDTO> findEventsWithoutLoss();

    /**
     * Finds events without report about loss
     *
     * @return list of desired events
     */
    List<EventDTO> findEventsWithoutFind();

    List<StatisticsDTO> getStatistics(StatisticsType type);
}
