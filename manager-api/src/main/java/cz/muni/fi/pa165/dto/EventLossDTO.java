package cz.muni.fi.pa165.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Adam Bananka
 */
public class EventLossDTO {
    private long id;

    @NotNull
    private ItemDTO item;

    @NotNull
    private UserDTO owner;
    @NotNull
    private LocalDate dateOfLoss;
    @NotNull
    @Size(min = 2, max = 256)
    private String placeOfLoss;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public LocalDate getDateOfLoss() {
        return dateOfLoss;
    }

    public void setDateOfLoss(LocalDate dateOfLoss) {
        this.dateOfLoss = dateOfLoss;
    }

    public String getPlaceOfLoss() {
        return placeOfLoss;
    }

    public void setPlaceOfLoss(String placeOfLoss) {
        this.placeOfLoss = placeOfLoss;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof EventDTO)) {
            return false;
        }
        EventDTO other = (EventDTO) obj;
        if (placeOfLoss != null ? !placeOfLoss.equals(other.getPlaceOfLoss()) : other.getPlaceOfLoss() != null) return false;
        if (dateOfLoss != null ? !dateOfLoss.equals(other.getDateOfLoss()) : other.getDateOfLoss() != null) return false;
        if (owner != null ? !owner.equals(other.getOwner()) : other.getOwner() != null) return false;
        return Objects.equals(this.item, other.getItem());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(this.item);
        result = prime * result + (owner != null ? owner.hashCode() : 0);
        result = prime * result + item.hashCode();
        result = prime * result + (dateOfLoss != null ? dateOfLoss.hashCode() : 0);
        result = prime * result + (placeOfLoss != null ? placeOfLoss.hashCode() : 0);

        return result;
    }
}
