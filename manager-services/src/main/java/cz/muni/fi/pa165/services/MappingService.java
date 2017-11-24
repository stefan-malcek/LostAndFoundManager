package cz.muni.fi.pa165.services;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

public interface MappingService {
    Mapper getMapper();
    <T> T mapTo(Object object, Class<T> mapToClass);
    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);
}
