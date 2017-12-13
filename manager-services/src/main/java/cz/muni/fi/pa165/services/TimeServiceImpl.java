package cz.muni.fi.pa165.services;

import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * An interface that defines a service access to the {@link Product} entity.
 */
@Service
public class TimeServiceImpl implements TimeService {

    @Override
    public Date getCurrentTime() {
        return new Date();
    }
}
