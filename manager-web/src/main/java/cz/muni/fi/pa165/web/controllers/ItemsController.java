/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.web.controllers;

import cz.muni.fi.pa165.dto.CategoryDTO;
import cz.muni.fi.pa165.dto.ItemDTO;
import cz.muni.fi.pa165.dto.QuestionsDTO;
import cz.muni.fi.pa165.facade.ItemFacade;
import cz.muni.fi.pa165.web.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.web.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.web.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.web.rest.ApiUris;
import java.util.Calendar;
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
 * @author stefa
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_ITEMS)
public class ItemsController {

    private final static Logger logger = LoggerFactory.getLogger(ItemsController.class);

    @Inject
    private ItemFacade itemFacade;

    //TODO: changeDTO to CreateItemDTO
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ItemDTO createItem(@RequestBody ItemDTO product) throws Exception {
        logger.debug("rest createItem()");
        try {
            Long id = itemFacade.create(product);
            return itemFacade.findById(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ItemDTO updateItem(@PathVariable("id") long id, @RequestBody ItemDTO newItem) throws Exception {
        logger.debug("rest updateItem({})", id);
        try {
            itemFacade.update(newItem);
            return itemFacade.findById(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    //TODO: update facade to consume Id
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteItem(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteItem({})", id);
        try {
            ItemDTO item = new ItemDTO();
            item.setId(id);
            itemFacade.remove(item);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ItemDTO getItem(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getItem({})", id);
        ItemDTO productDTO = itemFacade.findById(id);
        if (productDTO == null) {
            throw new ResourceNotFoundException();
        }
        return productDTO;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ItemDTO> getItems() {
        logger.debug("rest getAllItems()");
        return itemFacade.getAllItems();
    }

    //TODO: update facade to consume category Id
    @RequestMapping(value = "by_category_id/{category_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ItemDTO> getItemsByCategoryId(@PathVariable("category_id") long categoryId) throws Exception {
        logger.debug("rest getItemsByCategoryId({})", categoryId);
        CategoryDTO category = new CategoryDTO();
        category.setId(categoryId);
        List<ItemDTO> productDTO = itemFacade.findByCategory(category);
        if (productDTO == null) {
            throw new ResourceNotFoundException();
        }
        return productDTO;
    }

    //TODO: change path to {id}/can_return
    @RequestMapping(value = "/can_return", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final boolean canBeReturned(@RequestBody QuestionsDTO questions) throws Exception {
        logger.debug("rest canBeReturned()");
        try {
            return itemFacade.canBeReturned(questions);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    //TODO: change path to {id}/return
    @RequestMapping(value = "/return", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void returnItem(@RequestBody ItemDTO item) throws Exception {
        logger.debug("rest returnItem()");
        try {
            //TODO: implement service for time.
            itemFacade.itemReturnedToOwner(item, Calendar.getInstance().getTime());
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }
}