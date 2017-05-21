package com.javacook.chessgui.exception;


/**
 * Created by vollmer on 02.05.17.
 */
public class InvalidGameIdException extends RestException {

    public InvalidGameIdException(String message) {
        super(message);
    }
}
