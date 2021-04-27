package com.etnetera.hr.repository;

import com.etnetera.hr.model.JavaScriptFramework;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Spring data repository interface used for accessing the data in database.
 *
 * @author Etnetera, Jiri Grunwald
 */
public interface JavaScriptFrameworkRepository extends CrudRepository<JavaScriptFramework, Long> {

    /**
     * @return list of {@link JavaScriptFramework} or empty list if no record exists
     */
    @Override
    List<JavaScriptFramework> findAll();

    /**
     * Finds frameworks which name contains the given string value.
     *
     * @param name searching framework name
     * @return list of frameworks
     */
    List<JavaScriptFramework> findByNameContainingIgnoreCase(String name);
}