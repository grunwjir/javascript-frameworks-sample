package com.etnetera.hr.service;

import com.etnetera.hr.data.JavaScriptFrameworkIn;
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

    /**
     * Creates new {@link JavaScriptFramework} record.
     *
     * @param input data representing new record
     * @return created record
     */
    JavaScriptFramework createJavascriptFramework(JavaScriptFrameworkIn input);

    /**
     * Updates existing {@link JavaScriptFramework} record.
     *
     * @param input data representing the record
     * @return updated record
     */
    JavaScriptFramework updateJavascriptFramework(JavaScriptFrameworkIn input);


}