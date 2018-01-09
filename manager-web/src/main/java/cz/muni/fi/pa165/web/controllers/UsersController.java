package cz.muni.fi.pa165.web.controllers;

import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.UserUpdateDTO;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.web.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.web.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.web.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.web.rest.ApiUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * REST Controller for Users.
 *
 * @author Adam Bananka
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_USERS)
public class UsersController {

    private final static Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Inject
    private UserFacade userFacade;

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO registerUser(@RequestBody UserDTO user, @RequestBody String password) throws Exception {
        logger.debug("rest registerUser()");
        try {
            Long id = userFacade.register(user, password);
            return userFacade.findUserById(id);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO updateUser(@PathVariable("id") long id, @RequestBody UserUpdateDTO newUser) throws Exception {
        logger.debug("rest updateUser()");
        try {
            userFacade.update(newUser);
            return userFacade.findUserById(id);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new InvalidParameterException();
        }
    }

    @RequestMapping(value = "/{id}/pw", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO changeUserPassword(@PathVariable("id") long id, @RequestBody UserAuthenticateDTO user,
                                            @RequestBody String newPassword) throws Exception {
        logger.debug("rest changeUserPassword()");
        try {
            userFacade.changePassword(user, newPassword);
            return userFacade.findUserById(id);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new InvalidParameterException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteUser(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteUser()");
        try {
            userFacade.delete(id);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<UserDTO> findAllUsers() {
        logger.debug("rest findAllUsers()");
        return userFacade.findAllUsers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO findUser(@PathVariable("id") long id) throws Exception {
        logger.debug("rest findUser()", id);
        UserDTO user = userFacade.findUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        return user;
    }

    @RequestMapping(value = "/by_email/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO findUserByEmail(@PathVariable("email") String email) throws Exception {
        logger.debug("rest findUserByEmail()", email);
        UserDTO user = userFacade.findUserByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        return user;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final boolean authenticateUser(@RequestBody UserAuthenticateDTO user) throws Exception{
        logger.debug("rest authenticateUser()");
        try {
            return userFacade.authenticate(user);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new InvalidParameterException();
        }
    }

    @RequestMapping(value = "/{id}/is_admin", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final boolean isAdministrator(@PathVariable("id") long id) {
        logger.debug("rest isAdministrator()");
        UserDTO user = userFacade.findUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        try {
            return userFacade.isAdministrator(user);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new InvalidParameterException();
        }
    }
}
