/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Stefan Malcek
 */
@Service
public class CityServiceImpl implements CityService {

    private List<String> cities;

    public CityServiceImpl() {
        cities = new ArrayList<>();
    }

    @Override
    public List<String> getCities() {
        if (cities.isEmpty()) {
            loadXml();
        }

        return Collections.unmodifiableList(cities);
    }

    private void loadXml() {
        ClassLoader classLoader = getClass().getClassLoader();
        File xmlFile = new File(classLoader.getResource("cities.xml").getFile());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("city");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    cities.add(node.getTextContent());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException | DOMException ex) {
            System.out.printf(ex.getMessage());
        }
    }
}
