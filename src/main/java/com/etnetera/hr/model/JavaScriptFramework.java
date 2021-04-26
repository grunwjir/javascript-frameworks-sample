package com.etnetera.hr.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.List;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 *
 * @author Etnetera, Jiri Grunwald
 */
@ToString
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class JavaScriptFramework {

    /**
     * Unique identifier.
     */
    @Id
    @Column("JAVA_SCRIPT_FRAMEWORK_ID")
    private Long id;

    /**
     * Framework name.
     */
    private String name;

    /**
     * List of framework versions.
     */
    @MappedCollection(
            idColumn = "JAVA_SCRIPT_FRAMEWORK_ID",
            keyColumn = "JAVA_SCRIPT_FRAMEWORK_NO")
    private List<JavaScriptFrameworkVersion> versions;

    /**
     * All args constructor
     */
    public JavaScriptFramework(Long id, String name, List<JavaScriptFrameworkVersion> versions) {
        this.id = id;
        this.name = name;
        this.versions = versions;
    }
}