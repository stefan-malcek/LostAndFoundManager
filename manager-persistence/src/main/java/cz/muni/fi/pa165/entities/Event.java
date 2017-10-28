/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.entities;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Represents a user entity.
 * @author robhavlicek
 */
@Entity
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne(optional = false)
    private Item item;
    
    @ManyToOne(optional = true)
    private User owner;
    
    @Column(nullable = true, unique = false)
    private LocalDate dateOfLoss;
   
   
    @NotNull
    @Column(nullable = false, unique = false)
    private String placeOfLoss;
    
    @ManyToOne(optional = true)
    private User finder;
   
    @Column(nullable = true, unique = false)
    private LocalDate dateOfFind;
   
    @NotNull
    @Column(nullable = false, unique = false)
    private String placeOfFind;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }
    
    public User getFinder() {
        return finder;
    }

    public void setFinder(User user) {
        this.finder = user;
    }
    
    public String getPlaceOfFind() {
        return placeOfFind;
    }

    public void setPlaceOfFind(String place) {
        this.placeOfFind = place;
    }
    
    public LocalDate getDateOfFind() {
        return dateOfFind;
    }

    public void setDateOfFind(LocalDate date) {
        this.dateOfFind = date;
    }
    
    public LocalDate getDateOfLoss() {
        return dateOfLoss;
    }

    public void setDateOfLoss(LocalDate date) {
        this.dateOfLoss = date;
    }
    
    public String getPlaceOfLoss() {
        return placeOfLoss;
    }

    public void setPlaceOfLoss(String place) {
        this.placeOfLoss = place;
    }

    @Override
    public int hashCode() {
        int prime = 37;
        int result = 1;
        result = prime * result + Objects.hashCode(this.item);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Event)) {
            return false;
        }
        Event other = (Event) obj;
        return Objects.equals(this.item.getId(), other.getItem().getId());
    }
    
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id + 
                ", itemId=" + item.getId() + 
                ", dateOfLoss=" + dateOfLoss + 
                ", placeOfLoss=" + placeOfLoss + 
                ", dateOfFind=" + dateOfFind + 
                ", placeOfFind=" + placeOfFind + 
                ", owner=" + owner.getEmail() + 
                ", finder=" + finder.getEmail() + 
                '}';
    }
}