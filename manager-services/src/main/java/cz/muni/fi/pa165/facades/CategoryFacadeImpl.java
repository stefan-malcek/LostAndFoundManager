package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.dto.CategoryCreateDTO;
import cz.muni.fi.pa165.dto.CategoryDTO;
import cz.muni.fi.pa165.entities.Category;
import cz.muni.fi.pa165.facade.CategoryFacade;
import cz.muni.fi.pa165.services.CategoryService;
import cz.muni.fi.pa165.services.MappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 *
 * @author Stefan Malcek
 */
@Service
@Transactional
public class CategoryFacadeImpl implements CategoryFacade {

    @Inject
    private CategoryService categoryService;

    @Inject
    private MappingService mappingService;

    @Override
    public long createCategory(CategoryCreateDTO categoryCreateDTO) {
        Category category = mappingService.mapTo(categoryCreateDTO, Category.class);
        categoryService.create(category);
        return category.getId();
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = categoryService.findById(categoryDTO.getId());

        if (category != null) {
           category.setName(categoryDTO.getName());
           category.setDescription(categoryDTO.getDescription());
        }
    }

    @Override
    public void remove(long categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        categoryService.remove(category);
    }

    @Override
    public CategoryDTO getCategoryById(long id) {
        Category category = categoryService.findById(id);
        return (category == null) ? null : mappingService.mapTo(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO findByName(String categoryName) {
        Category category = categoryService.findByName(categoryName);
        return (category == null) ? null : mappingService.mapTo(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> allCategories = categoryService.findAll();
        return mappingService.mapTo(allCategories, CategoryDTO.class);
    }
}