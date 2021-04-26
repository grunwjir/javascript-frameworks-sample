package com.etnetera.hr.model;

/**
 * Represents that required JavaScript framework doesn't exist.
 *
 * @author Jiri Grunwald
 */
public class JavaScriptFrameworkNotFoundException extends RuntimeException {

    public JavaScriptFrameworkNotFoundException(String message) {
        super(message);
    }
}
