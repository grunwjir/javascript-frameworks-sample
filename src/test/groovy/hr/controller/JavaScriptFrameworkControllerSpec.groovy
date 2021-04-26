package hr.controller

import com.etnetera.hr.Application
import com.etnetera.hr.controller.JavaScriptFrameworkController
import com.etnetera.hr.data.JavaScriptFrameworkIn
import com.etnetera.hr.model.HypeLevel
import com.etnetera.hr.model.JavaScriptFramework
import com.etnetera.hr.model.JavaScriptFrameworkNotFoundException
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
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

    def "Framework should be created"() {
        given:
        javaScriptFrameworkService.createJavascriptFramework(_ as JavaScriptFrameworkIn) >> new JavaScriptFramework(versions: [])

        when:
        def results = mvc.perform(post("/frameworks")
                .content('{"name": "Vue.js", "versions": [{"version": "4.48.5", "deprecationDate": "2050-12-20", "hypeLevel": "HIGH"}]}')
                .contentType(MediaType.APPLICATION_JSON)
        )

        then:
        results.andExpect(status().isCreated())
        results.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    def "Framework shouldn't be created - missing required field 'name'"() {
        given:
        javaScriptFrameworkService.createJavascriptFramework(_ as JavaScriptFrameworkIn) >> new JavaScriptFramework(versions: [])

        when:
        def results = mvc.perform(post("/frameworks")
                .content('{"versions": [{"version": "4.48.5", "deprecationDate": "2050-12-20", "hypeLevel": "HIGH"}]}')
                .contentType(MediaType.APPLICATION_JSON)
        )

        then:
        results.andExpect(status().isBadRequest())
    }

    def "Framework should be updated"() {
        given:
        javaScriptFrameworkService.updateJavascriptFramework({ it.id == 5 } as JavaScriptFrameworkIn) >> new JavaScriptFramework(versions: [])

        when:
        def results = mvc.perform(put("/frameworks")
                .content('{"id": 5, "name": "Vue.js", "versions": [{"version": "4.48.5", "deprecationDate": "2050-12-20", "hypeLevel": "HIGH"}]}')
                .contentType(MediaType.APPLICATION_JSON)
        )

        then:
        results.andExpect(status().isOk())
        results.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    def "Framework shouldn't be updated - not exists"() {
        given:
        javaScriptFrameworkService.updateJavascriptFramework({ it.id == 5 } as JavaScriptFrameworkIn) >> { throw new JavaScriptFrameworkNotFoundException("Doesn't exist") }

        when:
        def results = mvc.perform(put("/frameworks")
                .content('{"id": 5, "name": "Vue.js", "versions": [{"version": "4.48.5", "deprecationDate": "2050-12-20", "hypeLevel": "HIGH"}]}')
                .contentType(MediaType.APPLICATION_JSON)
        )

        then:
        results.andExpect(status().isNotFound())
    }

    def "Framework should be deleted"() {
        given:
        javaScriptFrameworkService.deleteJavascriptFramework(5)

        when:
        def results = mvc.perform(delete("/frameworks/5"))

        then:
        results.andExpect(status().isNoContent())
    }

    def "Framework shouldn't be deleted - not exists"() {
        given:
        javaScriptFrameworkService.deleteJavascriptFramework(5) >> { throw new JavaScriptFrameworkNotFoundException("Doesn't exist") }

        when:
        def results = mvc.perform(delete("/frameworks/5"))

        then:
        results.andExpect(status().isNotFound())
    }
}