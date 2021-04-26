package com.etnetera.hr.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Represents that required JavaScript framework doesn't exist.
 *
 * @author Jiri Grunwald
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class JavaScriptFrameworkNotFoundException extends RuntimeException {

    public JavaScriptFrameworkNotFoundException(String message) {
        super(message);
    }
}
