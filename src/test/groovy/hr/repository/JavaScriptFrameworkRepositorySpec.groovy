package hr.repository

import com.etnetera.hr.Application
import com.etnetera.hr.model.HypeLevel
import com.etnetera.hr.repository.JavaScriptFrameworkRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

/**
 * Integration test for repository {@link JavaScriptFrameworkRepository}.
 *
 * @author Jiri Grunwald
 */
@ContextConfiguration(classes = Application)
@DataJdbcTest
class JavaScriptFrameworkRepositorySpec extends Specification {

    @Autowired
    JavaScriptFrameworkRepository javaScriptFrameworkRepository

    @Sql("insert_records.sql")
    @Unroll
    def "Searching by '#name' should found #shouldFound record(s)"(String name, int shouldFound) {
        expect:
        javaScriptFrameworkRepository.findByNameContainingIgnoreCase(name).size() == shouldFound

        where:
        name  || shouldFound
        "ang" || 1
        "Ang" || 1
        "abc" || 0
        "NG"  || 2
        ""    || 2
    }

    @Sql("insert_records.sql")
    def "Two records should be found"() {
        when:
        def frameworks = javaScriptFrameworkRepository.findAll()

        then:
        frameworks.size() == 2

        and: "verify mapping"
        verifyAll(frameworks[0]) {
            name == "Angular"
            versions != null
            versions.size() == 3
            verifyAll(versions[0]) {
                version == "JS"
                deprecationDate == LocalDate.of(2017, 12, 12)
                hypeLevel == HypeLevel.LOW
            }
        }
    }

    def "Should return empty list"() {
        when:
        def frameworks = javaScriptFrameworkRepository.findAll()

        then:
        frameworks != null
        frameworks.empty
    }

}