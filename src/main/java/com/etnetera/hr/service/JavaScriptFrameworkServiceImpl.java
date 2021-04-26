package com.etnetera.hr.service;

import com.etnetera.hr.model.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
