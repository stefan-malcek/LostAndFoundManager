/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dto.StatisticsDTO;
import java.util.Comparator;

/**
 *
 * @author stefa
 */
public class StatisticsComparator implements Comparator<StatisticsDTO> {

    @Override
    public int compare(StatisticsDTO o1, StatisticsDTO o2) {
        int result = o1.getTimes() - o2.getTimes();
        result *= -1;
        if (result == 0) {
            return o1.getCity().compareTo(o2.getCity());
        }
        return result;
    }

}
