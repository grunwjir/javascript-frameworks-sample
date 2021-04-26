package com.etnetera.hr.service;

import com.etnetera.hr.model.JavaScriptFramework;

import java.util.List;

/**
 * Service class for {@link JavaScriptFramework} entity.
 *
 * @author Jiri Grunwald
 */
public interface JavaScriptFrameworkService {

    /**
     * @return list of all JavaScript frameworks.
     */
    List<JavaScriptFramework> getJavaScriptFrameworks();
}