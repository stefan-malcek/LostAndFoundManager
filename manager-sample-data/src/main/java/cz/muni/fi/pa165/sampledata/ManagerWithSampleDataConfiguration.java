package cz.muni.fi.pa165.sampledata;

import cz.muni.fi.pa165.ServiceApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author Adam Bananka
 */
@Configuration
@Import(ServiceApplicationContext.class)
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class ManagerWithSampleDataConfiguration {

    private final static Logger log = LoggerFactory.getLogger(ManagerWithSampleDataConfiguration.class);

    @Autowired
    private SampleDataLoadingFacade sampleDataLoadingFacade;

    @PostConstruct
    public void dataLoading() throws IOException {
        log.debug("dataLoading()");
        sampleDataLoadingFacade.loadData();
    }
}
