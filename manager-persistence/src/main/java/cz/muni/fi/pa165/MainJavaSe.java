package cz.muni.fi.pa165;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

/**
 * Represents a main class.
 * @author Stefan Malcek
 */
public class MainJavaSe {

    public static void main(String[] args) throws SQLException {
        // The following line is here just to start up a in-memory database
        new AnnotationConfigApplicationContext(PersistenceApplicationContext.class);
    }
}