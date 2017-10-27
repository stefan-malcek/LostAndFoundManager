package cz.muni.fi.pa165.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Represents a category entity
 * @author Šimon Baláž
 */
@Entity
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;
    
    @NotNull
    @Column(nullable = false, unique = false)
    private String description;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
     @Override
    public int hashCode() {
        int prime = 37;
        int result = 1;
        result = prime * result + Objects.hashCode(this.name);
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
        if (!(obj instanceof Category)) {
            return false;
        }
        Category other = (Category) obj;
        return Objects.equals(this.name, other.getName());
    }
    
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id + 
                ", name=" + name + 
                ", description=" + description + 
                '}';
    }
    
}
