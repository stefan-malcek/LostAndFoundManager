package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.dto.CategoryCreateDTO;
import cz.muni.fi.pa165.dto.CategoryDTO;
import cz.muni.fi.pa165.facade.CategoryFacade;
import cz.muni.fi.pa165.services.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Stefan Malcek
 */
@Service
@Transactional
public class CategoryFacadeImpl implements CategoryFacade{

    @Autowired
    private CategoryService categoryService;
    
    @Override
    public long createCategory(CategoryCreateDTO categoryCreateDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CategoryDTO getCategoryById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
