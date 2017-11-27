/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.dto.enums.ItemColor;
import java.math.BigDecimal;

/**
 *
 * @author robhavlicek
 */
public class QuestionsDTO {

    private ItemColor color;
     
    private String description;

    private BigDecimal weight;

    private int height;

    private int width;

    private int depth;

    private ItemDTO selected;
    
    public ItemDTO getItem() {
        return selected;
    }

    public void setItem(ItemDTO item) {
        this.selected = item;
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
    
}
