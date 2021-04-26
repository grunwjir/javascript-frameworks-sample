package com.etnetera.hr.data;


import com.etnetera.hr.model.HypeLevel;
import com.etnetera.hr.model.JavaScriptFramework;
import com.etnetera.hr.model.JavaScriptFrameworkVersion;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data transfer object for {@link JavaScriptFramework}.
 *
 * @author Jiri Grunwald
 */
@ToString
public class JavaScriptFrameworkOut {

    private final JavaScriptFramework javaScriptFramework;

    public JavaScriptFrameworkOut(JavaScriptFramework javaScriptFramework) {
        this.javaScriptFramework = javaScriptFramework;
    }

    /**
     * @return Unique identifier
     */
    public Long getId() {
        return javaScriptFramework.getId();
    }

    /**
     * @return Framework name
     */
    public String getName() {
        return javaScriptFramework.getName();
    }

    /**
     * @return Framework versions
     */
    public List<JavaScriptFrameworkVersionOut> getVersions() {
        return javaScriptFramework.getVersions()
                .stream()
                .map(JavaScriptFrameworkVersionOut::new)
                .collect(Collectors.toList());
    }

    public static class JavaScriptFrameworkVersionOut {

        private final JavaScriptFrameworkVersion javaScriptFrameworkVersion;

        public JavaScriptFrameworkVersionOut(JavaScriptFrameworkVersion javaScriptFrameworkVersion) {
            this.javaScriptFrameworkVersion = javaScriptFrameworkVersion;
        }

        /**
         * @return Framework version name
         */
        public String getVersion() {
            return javaScriptFrameworkVersion.getVersion();
        }

        /**
         * @return Framework version deprecation date
         */
        public LocalDate getDeprecationDate() {
            return javaScriptFrameworkVersion.getDeprecationDate();
        }

        /**
         * @return Framework version hype level
         */
        public HypeLevel getHypeLevel() {
            return javaScriptFrameworkVersion.getHypeLevel();
        }
    }
}