/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.web.controllers;

import cz.muni.fi.pa165.dto.EventCreateDTO;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.dto.EventFindDTO;
import cz.muni.fi.pa165.dto.EventLossDTO;
import cz.muni.fi.pa165.dto.StatisticsDTO;
import cz.muni.fi.pa165.enums.StatisticsType;
import cz.muni.fi.pa165.facade.EventFacade;
import cz.muni.fi.pa165.web.StatisticsTypeEnumConverter;
import cz.muni.fi.pa165.web.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.web.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.web.rest.ApiUris;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.inject.Inject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Šimon Baláž
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_EVENTS)
public class EventsController {

    private final static Logger logger = LoggerFactory.getLogger(EventsController.class);

    @Inject
    private EventFacade eventFacade;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(StatisticsType.class, new StatisticsTypeEnumConverter());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EventDTO createEvent(@RequestBody EventCreateDTO eventCreate) throws Exception {
        logger.debug("rest createEvent()");
        try {
            Long id = eventFacade.createEvent(eventCreate);
            return eventFacade.findEventById(id);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(value = "/add_loosing", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EventDTO addLoosing(@RequestBody EventLossDTO eventLoss) throws Exception {
        logger.debug("rest addLoosing()");
        try {
            Long id = eventFacade.addLoosing(eventLoss);
            return eventFacade.findEventById(id);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/add_finding", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final EventDTO addFinding(@RequestBody EventFindDTO eventFind) throws Exception {
        logger.debug("rest addFinding()");
        try {
            Long id = eventFacade.addFinding(eventFind);
            return eventFacade.findEventById(id);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteEvent(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteEvent({})", id);
        try {
            eventFacade.deleteEvent(id);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "check_id/{check_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final boolean checkEventResolved(@PathVariable("check_id") long check_id) throws Exception {
        logger.debug("rest checkEventResolved({})", check_id);
        try {
            boolean resolved = eventFacade.checkEventResolved(check_id);
            return resolved;
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final EventDTO getEvent(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getEvent({})", id);
        EventDTO event = eventFacade.findEventById(id);
        if (event == null) {
            throw new ResourceNotFoundException();
        }
        return event;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EventDTO> getEvents() {
        logger.debug("rest getAllEvents()");
        return eventFacade.findAllEvents();
    }

    @RequestMapping(value = "/by_item_id/{item_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final EventDTO getEventByItemId(@PathVariable("item_id") long itemId) throws Exception {
        logger.debug("rest getEventsByItemId({})", itemId);
        EventDTO event = eventFacade.findEventByItem(itemId);
        if (event == null) {
            throw new ResourceNotFoundException();
        }
        return event;
    }

    @RequestMapping(value = "/by_owner_id/{owner_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EventDTO> getEventByOwnerId(@PathVariable("owner_id") long ownerId) throws Exception {
        logger.debug("rest getEventsByOwnerId({})", ownerId);
        List<EventDTO> events = eventFacade.findEventsByOwner(ownerId);
        if (events == null) {
            throw new ResourceNotFoundException();
        }
        return events;
    }

    @RequestMapping(value = "/by_finder_id/{finder_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EventDTO> getEventByFinderId(@PathVariable("finder_id") long finderId) throws Exception {
        logger.debug("rest getEventsByFinderId({})", finderId);
        List<EventDTO> events = eventFacade.findEventsByFinder(finderId);
        if (events == null) {
            throw new ResourceNotFoundException();
        }
        return events;
    }

    @RequestMapping(value = "/by_place_of_loss/{place_of_loss}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EventDTO> getEventByPlaceOfLoss(@PathVariable("place_of_loss") String placeOfLoss) throws Exception {
        logger.debug("rest getEventsByPlaceOfLoss({})", placeOfLoss);
        List<EventDTO> events = eventFacade.findEventsByPlaceOfLoss(placeOfLoss);
        if (events == null) {
            throw new ResourceNotFoundException();
        }
        return events;
    }

    @RequestMapping(value = "/by_place_of_find/{place_of_find}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EventDTO> getEventByPlaceOfFind(@PathVariable("place_of_find") String placeOfFind) throws Exception {
        logger.debug("rest getEventsByPlaceOfFind({})", placeOfFind);
        List<EventDTO> events = eventFacade.findEventsByPlaceOfFind(placeOfFind);
        if (events == null) {
            throw new ResourceNotFoundException();
        }
        return events;
    }

    @RequestMapping(value = "/by_date_of_loss/{date_of_loss}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EventDTO> getEventByDateOfLoss(@PathVariable("date_of_loss") Date dateOfLoss) throws Exception {
        logger.debug("rest getEventsByDateOfLoss({})", dateOfLoss);
        List<EventDTO> events = eventFacade.findEventsByDateOfLoss(dateOfLoss);
        if (events == null) {
            throw new ResourceNotFoundException();
        }
        return events;
    }

    @RequestMapping(value = "/by_date_of_find/{date_of_find}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EventDTO> getEventByDateOfFind(@PathVariable("date_of_find") Date dateOfFind) throws Exception {
        logger.debug("rest getEventsByPlaceOfFind({})", dateOfFind);
        List<EventDTO> events = eventFacade.findEventsByDateOfFind(dateOfFind);
        if (events == null) {
            throw new ResourceNotFoundException();
        }
        return events;
    }

    @RequestMapping(value = "/without_loss", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EventDTO> getEventsWithoutLoss() {
        logger.debug("rest getEventsWithoutLoss()");
        return eventFacade.findEventsWithoutLoss();
    }    
    
    @RequestMapping(value = "/without_find" ,method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<EventDTO> getEventsWithoutFind() {
        logger.debug("rest getEventsWithoutFind()");
        return eventFacade.findEventsWithoutFind();
    }


    //Usage /statistics?type=loss
    @RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<StatisticsDTO> getStatistics(@RequestParam(name = "type", required = true) StatisticsType type) {
        logger.debug("rest getStatistics()");
        return eventFacade.getStatistics(type);
    }

}
