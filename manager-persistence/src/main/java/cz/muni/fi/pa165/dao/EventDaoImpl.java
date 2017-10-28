/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Event;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author robhavlicek
 */
@Repository
public class EventDaoImpl implements EventDao {

    
    @PersistenceContext
    private EntityManager em;
 
    @Override
    public void create(Event event)  {
    if (event == null)
            throw new IllegalArgumentException("event cannot be null.");
        em.persist(event);        
    }

    @Override
    public void delete(Event event) throws IllegalArgumentException {
          if (event == null) {
            throw new IllegalArgumentException("Event cannot be null.");
        }
        em.remove(event);
    }

    @Override
    public Event findById(long id) {
        if (id <= 0) 
            throw new IllegalArgumentException("Id cannot be lower than or equal to zero");
        return em.find(Event.class, id);
    }

    @OneToOne
    @Override
    public Event findByItemId(long id) {
        if (id <= 0) 
            throw new IllegalArgumentException("Id cannot be lower than or equal to zero");
        return em.createQuery("SELECT e FROM Event e WHERE e.itemId = :itemId ",
				Event.class).setParameter("itemId",id).getSingleResult();
    }

    @Override
    public List<Event> findAll() {
       return em.createQuery("select e from Event e", Event.class)
            .getResultList();
    }

    @Override
    public List<Event> findEventByFinderId(long id) {
          if (id <= 0) 
            throw new IllegalArgumentException("Id cannot be lower than or equal to zero");
          return em.createQuery("SELECT e FROM Event e WHERE e.finderId = :finderId ",
		Event.class).setParameter("finderId",id).getResultList();
    }

    @Override
    public List<Event> findEventByOwnerId(long id) {
          if (id <= 0) 
            throw new IllegalArgumentException("Id cannot be lower than or equal to zero");
          return em.createQuery("SELECT e FROM Event e WHERE e.ownerId = :ownerId ",
		Event.class).setParameter("ownerId",id).getResultList();
    }

    @Override
    public List<Event> findEventByPlaceOfLoss(String place) {
         return em.createQuery("SELECT e FROM Event e WHERE e.placeOfLoss like :placeOfLoss ",
		Event.class).setParameter("placeOfLoss","%"+place+"%").getResultList();
    }

    @Override
    public List<Event> findEventByPlaceOfFind(String place) {
            return em.createQuery("SELECT e FROM Event e WHERE e.placeOfFind like :placeOfFind ",
		Event.class).setParameter("placeOfFind","%"+place+"%").getResultList();
    }

    @Override
    public List<Event> findEventByDateOfFind(LocalDate date) {
            return em.createQuery("SELECT e FROM Event e WHERE e.dateOfFind = :dateOfFind ",
		Event.class).setParameter("dateOfFind",date).getResultList();
    }

    @Override
    public List<Event> findEventByDateOfLoss(LocalDate date) {
         return em.createQuery("SELECT e FROM Event e WHERE e.dateOfLoss = :dateOfLoss",
		Event.class).setParameter("dateOfLoss",date).getResultList();
    }

    @Override
    public void update(Event event) {
        if (event == null) 
            throw new IllegalArgumentException("Event cannot be null.");
         em.merge(event); 
    }
}
