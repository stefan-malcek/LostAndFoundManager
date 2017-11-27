/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.Item;
import cz.muni.fi.pa165.entities.User;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
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

    @Override
    public Event findByItem(Item item) {
          if (item == null)
            throw new IllegalArgumentException("Item cannot be null.");
        return em.createQuery("SELECT e FROM Event e WHERE item = :item ",
				Event.class).setParameter("item",item).getSingleResult();
    }

    @Override
    public List<Event> findAll() {
       return em.createQuery("select e from Event e", Event.class)
            .getResultList();
    }

    @Override
    public List<Event> findEventByFinder(User user) {
         if (user == null)
            throw new IllegalArgumentException("User cannot be null.");
          return em.createQuery("SELECT e FROM Event e WHERE e.finder = :finder ",
		Event.class).setParameter("finder",user).getResultList();
    }

    @Override
    public List<Event> findEventByOwner(User user) {
          if (user == null)
            throw new IllegalArgumentException("User cannot be null.");
          return em.createQuery("SELECT e FROM Event e WHERE e.owner = :owner",
		Event.class).setParameter("owner",user).getResultList();
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
    public List<Event> findEventByDateOfFind(Date date) {
            return em.createQuery("SELECT e FROM Event e WHERE e.dateOfFind = :dateOfFind ",
		Event.class).setParameter("dateOfFind",date).getResultList();
    }

    @Override
    public List<Event> findEventByDateOfLoss(Date date) {
         return em.createQuery("SELECT e FROM Event e WHERE e.dateOfLoss = :dateOfLoss",
		Event.class).setParameter("dateOfLoss",date).getResultList();
    }
}
