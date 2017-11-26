/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import org.springframework.dao.DataAccessException;

/**
 *
 * @author robhavlicek
 */
public class LostAndFoundManagerDataAccessException extends DataAccessException {

    public LostAndFoundManagerDataAccessException(String msg) {
        super(msg);
    }
    
    public LostAndFoundManagerDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

