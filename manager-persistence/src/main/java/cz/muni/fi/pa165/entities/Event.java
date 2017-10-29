/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.entities;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

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
    @OneToOne(optional = false)
    private Item item;

    @ManyToOne()
    private User owner;
    private LocalDate dateOfLoss;
    private String placeOfLoss;

    @ManyToOne()
    private User finder;
    private LocalDate dateOfFind;
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
        result = prime * result + (finder != null ? finder.hashCode() : 0);
        result = prime * result + (owner != null ? owner.hashCode() : 0);
        result = prime * result + (item != null ? item.hashCode() : 0);
        result = prime * result + (dateOfLoss != null ? dateOfLoss.hashCode() : 0);
        result = prime * result + (dateOfFind != null ? dateOfFind.hashCode() : 0);
        result = prime * result + (placeOfLoss != null ? placeOfLoss.hashCode() : 0);
        result = prime * result + (placeOfFind != null ? placeOfFind.hashCode() : 0);

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
        if (!placeOfFind.equals(other.getPlaceOfFind())) return false;
        if (!placeOfLoss.equals(other.getPlaceOfLoss())) return false;
        if (!dateOfLoss.equals(other.getDateOfLoss())) return false;
        if (!dateOfFind.equals(other.getDateOfFind())) return false;
        if (!finder.equals(other.getFinder())) return false;
        if (!owner.equals(other.getOwner())) return false;
        if (item.getId() != other.getItem().getId()) return false;
        return Objects.equals(this.item, other.getItem());
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