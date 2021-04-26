package com.etnetera.hr.controller;

import com.etnetera.hr.data.JavaScriptFrameworkIn;
import com.etnetera.hr.data.JavaScriptFrameworkOut;
import com.etnetera.hr.model.JavaScriptFramework;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @Operation(summary = "Creates new JavaScript framework and their versions")
    @PostMapping(value = "/frameworks")
    @ResponseStatus(HttpStatus.CREATED)
    public JavaScriptFrameworkOut createFramework(@RequestBody @Valid JavaScriptFrameworkIn javaScriptFramework) {
        JavaScriptFramework created = javaScriptFrameworkService.createJavascriptFramework(javaScriptFramework);

        return new JavaScriptFrameworkOut(created);
    }

    @Operation(summary = "Updates existing JavaScript framework and their versions")
    @PutMapping(value = "/frameworks")
    public JavaScriptFrameworkOut updateFramework(@RequestBody @Valid JavaScriptFrameworkIn javaScriptFramework) {
        JavaScriptFramework created = javaScriptFrameworkService.updateJavascriptFramework(javaScriptFramework);

        return new JavaScriptFrameworkOut(created);
    }

    @Operation(summary = "Remove the given JavaScript framework")
    @DeleteMapping(value = "/frameworks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFramework(@PathVariable @NotNull Long id) {
        javaScriptFrameworkService.deleteJavascriptFramework(id);
    }

}