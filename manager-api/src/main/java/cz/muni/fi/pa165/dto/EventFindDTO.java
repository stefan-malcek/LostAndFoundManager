package cz.muni.fi.pa165.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Adam Bananka
 */
public class EventFindDTO {
    private long id;

    @NotNull
    private ItemDTO item;

    @NotNull
    private UserDTO finder;
    @NotNull
    private LocalDate dateOfFind;
    @NotNull
    @Size(min = 2, max = 256)
    private String placeOfFind;

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

    public UserDTO getFinder() {
        return finder;
    }

    public void setFinder(UserDTO finder) {
        this.finder = finder;
    }

    public LocalDate getDateOfFind() {
        return dateOfFind;
    }

    public void setDateOfFind(LocalDate dateOfFind) {
        this.dateOfFind = dateOfFind;
    }

    public String getPlaceOfFind() {
        return placeOfFind;
    }

    public void setPlaceOfFind(String placeOfFind) {
        this.placeOfFind = placeOfFind;
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
        if (placeOfFind != null ? !placeOfFind.equals(other.getPlaceOfFind()) : other.getPlaceOfFind() != null) return false;
        if (dateOfFind != null ? !dateOfFind.equals(other.getDateOfFind()) : other.getDateOfFind() != null) return false;
        if (finder != null ? !finder.equals(other.getFinder()) : other.getFinder() != null) return false;
        return Objects.equals(this.item, other.getItem());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(this.item);
        result = prime * result + (finder != null ? finder.hashCode() : 0);
        result = prime * result + item.hashCode();
        result = prime * result + (dateOfFind != null ? dateOfFind.hashCode() : 0);
        result = prime * result + (placeOfFind != null ? placeOfFind.hashCode() : 0);

        return result;
    }
}
