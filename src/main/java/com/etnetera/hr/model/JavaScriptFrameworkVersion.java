package com.etnetera.hr.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;


/**
 * Describes version of certain JavaScript framework. Value class for {@link JavaScriptFramework}.
 *
 * @author Jiri Grunwald
 */
@ToString
@NoArgsConstructor
@Getter
public class JavaScriptFrameworkVersion {

    /**
     * Name of JavaScript framework version.
     */
    private String version;

    /**
     * The date the version was marked as deprecated. Null or future date means up-to-date framework version.
     */
    private LocalDate deprecationDate;

    /**
     * Admiration level of the JavaScript framework version.
     */
    private HypeLevel hypeLevel;

    /**
     * All args constructor
     */
    public JavaScriptFrameworkVersion(String version, LocalDate deprecationDate, HypeLevel hypeLevel) {
        this.version = version;
        this.deprecationDate = deprecationDate;
        this.hypeLevel = hypeLevel;
    }
}