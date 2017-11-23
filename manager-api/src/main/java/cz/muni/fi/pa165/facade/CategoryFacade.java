package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CategoryCreateDTO;
import cz.muni.fi.pa165.dto.CategoryDTO;
import java.util.List;

/**
 * Created by stefa on 21.11.2017.
 */
public interface CategoryFacade {
    long createCategory(CategoryCreateDTO categoryCreateDTO);  
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(long id);
}
