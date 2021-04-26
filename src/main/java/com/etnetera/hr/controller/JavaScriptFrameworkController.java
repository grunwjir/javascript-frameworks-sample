package com.etnetera.hr.controller;

import com.etnetera.hr.data.JavaScriptFrameworkOut;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple REST controller for accessing application logic.
 *
 * @author Etnetera, Jiri Grunwald
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class JavaScriptFrameworkController {

    private final JavaScriptFrameworkService javaScriptFrameworkService;

    @Operation(summary = "Provides all JavaScript frameworks and their versions")
    @GetMapping(value = "/frameworks")
    public List<JavaScriptFrameworkOut> frameworks() {
        return javaScriptFrameworkService.getJavaScriptFrameworks()
                .stream()
                .map(JavaScriptFrameworkOut::new)
                .collect(Collectors.toList());
    }

}