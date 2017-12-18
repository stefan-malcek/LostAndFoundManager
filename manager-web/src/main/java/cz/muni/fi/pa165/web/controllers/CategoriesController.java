/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.web.controllers;

import cz.muni.fi.pa165.dto.CategoryCreateDTO;
import cz.muni.fi.pa165.dto.CategoryDTO;
import cz.muni.fi.pa165.facade.CategoryFacade;
import cz.muni.fi.pa165.web.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.web.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.web.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.web.rest.ApiUris;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author robhavlicek@gmail.com
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_CATEGORIES)
public class CategoriesController {

    private final static Logger logger = LoggerFactory.getLogger(ItemsController.class);

    @Inject
    private CategoryFacade categoryFacade;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CategoryDTO createCategory(@RequestBody CategoryCreateDTO category) throws Exception {
        logger.debug("rest createCategory()");
        try {
            Long id = categoryFacade.createCategory(category);
            return categoryFacade.getCategoryById(id);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final CategoryDTO updateCategory(@PathVariable("id") long id, @RequestBody CategoryDTO newCategory) throws Exception {
        logger.debug("rest updateCategory({})", id);
        try {
            categoryFacade.updateCategory(newCategory);
            return categoryFacade.getCategoryById(id);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new InvalidParameterException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteCategory(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteCategory({})", id);
        try {
            categoryFacade.remove(id);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final CategoryDTO getCategory(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getCategory({})", id);
        CategoryDTO categoryDTO = categoryFacade.getCategoryById(id);
        if (categoryDTO == null) {
            throw new ResourceNotFoundException();
        }
        return categoryDTO;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CategoryDTO> getCategories() {
        logger.debug("rest getAllCategories()");
        return categoryFacade.getAllCategories();
    }
}