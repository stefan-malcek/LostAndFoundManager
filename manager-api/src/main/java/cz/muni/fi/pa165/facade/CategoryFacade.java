package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CategoryCreateDTO;
import cz.muni.fi.pa165.dto.CategoryDTO;
import java.util.List;

/**
 * Created by Stefan malcek on 21.11.2017.
 */
public interface CategoryFacade {
    long createCategory(CategoryCreateDTO categoryCreateDTO);
    void updateCategory(CategoryDTO categoryDTO);
    void remove(long categoryId);
    CategoryDTO getCategoryById(long id);
    CategoryDTO findByName(String categoryName);
    List<CategoryDTO> getAllCategories();
}
