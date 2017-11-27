/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;
import java.util.Date;
import java.util.List;

import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.entities.User;
import java.time.LocalDate;


/**
 * Represents a Data Access Object for Event entity.
 * @author robhavlicek
 */
public interface EventDao {
    /**
     * Stores a new event in the application.
     * @param event Event to create
     */
    void create(Event event);
    
    /**
     * Deletes the event with given id.
     * @param event Event to delete
     */
    void delete(Event event);
        
    /**
     * Retrieves the event with given id.
     * @param id Id of the event
     * @return {@code Event} with given id
     */
    Event findById(long id);
    
    /**
     * Retrieves the event with given item.
     * @param item The lost item.
     * @return {@code Event} with given id
     */
    Event findByItem(Item item);
    
    /**
     * Retrieves all events with given finder.
     * @param finder User who found item.
     * @return {@code Event} with given id
     */
    List<Event> findEventByFinder(User finder);
    
    /**
     * Retrieves all events with given owner.
     * @param owner User who lost item.
     * @return {@code Event} with given id
     */
    List<Event> findEventByOwner(User owner);
    
    /**
     * Retrieves all events with given place of loss.
     * @param place Place of loss of the event
     * @return {@code Event} with given id 
     * or {@code null} if was not found. 
     */
    List<Event> findEventByPlaceOfLoss(String place);
    
    /**
     * Retrieves all events with given place of find.
     * @param place Place of find of the event
     * @return {@code Event} with given id 
     * or {@code null} if was not found. 
     */
    List<Event> findEventByPlaceOfFind(String place);
    
    /**
     * Retrieves all events with given date of find.
     * @param date Date of find of the event
     * @return {@code Event} with given id 
     * or {@code null} if was not found. 
     */
    List<Event> findEventByDateOfFind(Date date);
    
    /**
     * Retrieves all events with given date of loss.
     * @param date Date of loss of the event
     * @return {@code Event} with given id 
     * or {@code null} if was not found. 
     */
    List<Event> findEventByDateOfLoss(Date date);
    
    /**
     * Retrieves all events.
     * @return {@code List} of events
     */
    List<Event> findAll();
}
