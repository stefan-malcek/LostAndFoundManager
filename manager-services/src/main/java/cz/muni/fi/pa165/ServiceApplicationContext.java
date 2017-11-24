package cz.muni.fi.pa165;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceApplicationContext.class)
//@ComponentScan(basePackageClasses={OrderServiceImpl.class, CategoryFacadeImpl.class})
public class ServiceApplicationContext {


    @Bean
    public Mapper mapper(){
        DozerBeanMapper dozer = new DozerBeanMapper();
        //dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    /**
     * Custom config for Dozer if needed
     * @author nguyen
     *

    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(Category.class, CategoryDTO.class);
        }
    }*/

}
