package hr.controller

import com.etnetera.hr.Application
import com.etnetera.hr.controller.JavaScriptFrameworkController
import com.etnetera.hr.model.HypeLevel
import com.etnetera.hr.model.JavaScriptFramework
import com.etnetera.hr.model.JavaScriptFrameworkVersion
import com.etnetera.hr.service.JavaScriptFrameworkServiceImpl
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * Tests {@link JavaScriptFrameworkController}.
 *
 * @author Jiri Grunwald
 */
@ContextConfiguration(classes = Application)
@WebMvcTest(controllers = [JavaScriptFrameworkController])
class JavaScriptFrameworkControllerSpec extends Specification {

    @Autowired
    MockMvc mvc

    @SpringBean
    JavaScriptFrameworkServiceImpl javaScriptFrameworkService = Stub()

    def "Get frameworks should return two records"() {
        given:
        javaScriptFrameworkService.getJavaScriptFrameworks() >> [
                new JavaScriptFramework(10, "Vue.js",
                        List.of(new JavaScriptFrameworkVersion("4.48.5", LocalDate.of(2050, 12, 20), HypeLevel.HIGH))),
                new JavaScriptFramework(20, "Non-released-JS-Framework", List.of())
        ]

        when:
        def results = mvc.perform(get("/frameworks"))

        then:
        results.andExpect(status().isOk())
        results.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        results.andExpect(jsonPath('$.length()').value(2))

        and: "First framework check"
        results.andExpect(jsonPath('$[0].name').value("Vue.js"))
        results.andExpect(jsonPath('$[0].versions.length()').value(1))
        results.andExpect(jsonPath('$[0].versions[0].version').value("4.48.5"))
        results.andExpect(jsonPath('$[0].versions[0].deprecationDate').value("2050-12-20"))
        results.andExpect(jsonPath('$[0].versions[0].hypeLevel').value("HIGH"))

        and: "Second framework check"
        results.andExpect(jsonPath('$[1].name').value("Non-released-JS-Framework"))
        results.andExpect(jsonPath('$[1].versions.length()').value(0))
    }
}