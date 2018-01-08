/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dto;

/**
 *
 * @author Stefan Malcek
 */
public class StatisticsDTO {

    private String city;
    private int times;

    public StatisticsDTO(String city, int times) {
        this.city = city;
        this.times = times;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
