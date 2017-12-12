package cz.muni.fi.pa165.sampledata;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Loads some sample data to populate the LostAndFoundManager database.
 *
 * @author Adam Bananka
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade{


    @Override
    public void loadData() throws IOException {

    }
}
