package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CategoryCreateDTO;
import cz.muni.fi.pa165.dto.CategoryDTO;
import java.util.List;

/**
 * Created by Stefan Malcek on 21.11.2017.
 */
public interface CategoryFacade {

    /**
     * Stores a new category.
     * @param categoryCreateDTO Category to create
     */
    long createCategory(CategoryCreateDTO categoryCreateDTO);

    /**
     * Updates the category.
     * @param categoryDTO Category to update
     */
    void updateCategory(CategoryDTO categoryDTO);

    /**
     * Deletes the category with given id.
     * @param categoryId category id
     */
    void remove(long categoryId);

    /**
     * Retrieves the category with given id.
     * @param id Id of the category
     * @return {@code CategoryDTO} with given id
     */
    CategoryDTO getCategoryById(long id);

    /**
     * Retrieves the category with given name.
     * @param categoryName Name of the category
     * @return {@code CategoryDTO} with given id
     * or {@code null} if was not found.
     */
    CategoryDTO findByName(String categoryName);

    /**
     * Retrieves all categories.
     * @return {@code List} of categories
     */
    List<CategoryDTO> getAllCategories();
}
