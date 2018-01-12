/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.ServiceApplicationContext;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Stefan Malcek
 */
@ContextConfiguration(classes = ServiceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class CityServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CityService cityService;

    @Test
    public void getCities_xml_loads() {
        List<String> cities = cityService.getCities();
        
        Assert.assertFalse(cities.isEmpty());
    }
}
