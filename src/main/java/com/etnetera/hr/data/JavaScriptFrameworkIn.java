package com.etnetera.hr.data;

import com.etnetera.hr.model.HypeLevel;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * Input data class for {@link com.etnetera.hr.model.JavaScriptFramework}.
 *
 * @author Jiri Grunwald
 */
@Data
public class JavaScriptFrameworkIn {

    private Long id;

    @NotBlank
    @Size(max = 30)
    private String name;
    @Valid
    private List<JavaScriptFrameworkVersionIn> versions;

    @Data
    public static class JavaScriptFrameworkVersionIn {
        @NotBlank
        @Size(max = 30)
        private String version;
        private LocalDate deprecationDate;
        @NotNull
        private HypeLevel hypeLevel;
    }
}