package com.javacook.chessgui.exception;

import java.net.URI;


/**
 * Created by vollmer on 05.05.17.
 */
public class NotFoundException extends RestException {

    public NotFoundException(URI uri) {
        super(uri == null? null : uri.toString());
    }
}
