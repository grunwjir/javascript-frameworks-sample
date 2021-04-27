package com.etnetera.hr.service;

import com.etnetera.hr.data.JavaScriptFrameworkIn;
import com.etnetera.hr.model.JavaScriptFramework;
import com.etnetera.hr.model.JavaScriptFrameworkNotFoundException;
import com.etnetera.hr.model.JavaScriptFrameworkVersion;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link JavaScriptFrameworkService}.
 *
 * @author Jiri Grunwald
 */
@Service
@RequiredArgsConstructor
public class JavaScriptFrameworkServiceImpl implements JavaScriptFrameworkService {

    private final JavaScriptFrameworkRepository javaScriptFrameworkRepository;

    @Override
    @Transactional(readOnly = true)
    public List<JavaScriptFramework> getJavaScriptFrameworks() {
        return javaScriptFrameworkRepository.findAll();
    }

    @Override
    @Transactional
    public JavaScriptFramework createJavascriptFramework(JavaScriptFrameworkIn input) {
        // map versions
        List<JavaScriptFrameworkVersion> versions = mapVersions(input.getVersions());

        // map framework
        JavaScriptFramework framework = new JavaScriptFramework(null, input.getName(), versions);

        // persist
        return javaScriptFrameworkRepository.save(framework);
    }

    @Override
    @Transactional
    public JavaScriptFramework updateJavascriptFramework(JavaScriptFrameworkIn input) {
        boolean exists = javaScriptFrameworkRepository.existsById(input.getId());

        if (!exists) {
            throw new JavaScriptFrameworkNotFoundException("JavaScript framework ID: " + input.getId() + "doesn't " +
                    "exist");
        }

        // map versions
        List<JavaScriptFrameworkVersion> versions = mapVersions(input.getVersions());

        // map framework
        JavaScriptFramework framework = new JavaScriptFramework(input.getId(), input.getName(), versions);

        // persist
        return javaScriptFrameworkRepository.save(framework);
    }

    @Override
    @Transactional
    public void deleteJavascriptFramework(Long id) {
        boolean exists = javaScriptFrameworkRepository.existsById(id);

        if (!exists) {
            throw new JavaScriptFrameworkNotFoundException("JavaScript framework ID: " + id + "doesn't exist");
        }

        javaScriptFrameworkRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JavaScriptFramework> searchJavaScriptFrameworks(String name) {
        return javaScriptFrameworkRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Map input objects to domain objects.
     *
     * @param versions list of framework versions
     * @return list of framework versions
     */
    private List<JavaScriptFrameworkVersion> mapVersions(List<JavaScriptFrameworkIn.JavaScriptFrameworkVersionIn> versions) {
        if (versions == null) {
            return new ArrayList<>();
        } else {
            return versions.stream()
                    .map(v -> new JavaScriptFrameworkVersion(v.getVersion(),
                            v.getDeprecationDate(),
                            v.getHypeLevel()))
                    .collect(Collectors.toList());
        }
    }

}