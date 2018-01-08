/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.web;

import cz.muni.fi.pa165.enums.StatisticsType;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author Stefan Malcek
 * https://machiel.me/post/java-enums-as-request-parameters-in-spring-4/
 */
public class StatisticsTypeEnumConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        String capitalized = text.toUpperCase();
        StatisticsType type = StatisticsType.valueOf(capitalized);
        setValue(type);
    }
}
