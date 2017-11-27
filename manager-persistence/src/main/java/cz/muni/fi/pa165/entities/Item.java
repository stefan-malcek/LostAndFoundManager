package cz.muni.fi.pa165.entities;

import cz.muni.fi.pa165.enums.ItemColor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * Represents a user entity.
 *
 * @author Adam Bananka
 */
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(nullable = false)
    @Size(min = 1, max = 256)
    private String name;

    @NotNull
    @ManyToOne(optional = false)
    private Category category;

    @Enumerated
    private ItemColor color;

    @NotNull
    @Column(nullable = false)
    private String description;

    @DecimalMin("0.00")
    private BigDecimal weight;

    @Min(0)
    private int height;

    @Min(0)
    private int width;

    @Min(0)
    private int depth;

    private String photoUri;

    @Temporal(TemporalType.DATE)
    private Date returned;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ItemColor getColor() {
        return color;
    }

    public void setColor(ItemColor color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public Date getReturned() {
        return returned;
    }

    public void setReturned(Date returned) {
        this.returned = returned;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Item)) {
            return false;
        }

        Item item = (Item) obj;

        if (!name.equals(item.getName()) ) return false;
        if (!category.equals(item.getCategory()) ) return false;
        if (color != null ? color != item.getColor() : item.getColor() != null) return false;
        if (weight != null ? !weight.equals(item.getWeight()) : item.getWeight() != null) return false;
        if (height != item.getHeight()) return false;
        if (width != item.getWidth()) return false;
        if (depth != item.getDepth()) return false;
        return returned != null ? returned.equals(item.getReturned()) : item.getReturned() == null;
    }

    @Override
    public int hashCode() {
        int prime = 37;
        int result = 1;

        result = prime * result + name.hashCode();
        result = prime * result + category.hashCode();
        result = prime * result + (color != null ? color.hashCode() : 0);
        result = prime * result + (weight != null ? weight.hashCode() : 0);
        result = prime * result + height;
        result = prime * result + width;
        result = prime * result + depth;
        result = prime * result + (returned != null ? returned.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", color=" + color +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", width=" + width +
                ", depth=" + depth +
                ", photoUri='" + photoUri + '\'' +
                ", returned=" + returned +
                '}';
    }
}
