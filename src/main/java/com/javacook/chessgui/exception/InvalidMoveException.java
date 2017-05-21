package com.javacook.chessgui.exception;


/**
 * Created by vollmer on 02.05.17.
 */
public class InvalidMoveException extends RestException {

    public InvalidMoveException(String message) {
        super(message);
    }
}
