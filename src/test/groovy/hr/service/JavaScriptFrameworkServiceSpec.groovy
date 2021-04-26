package hr.service

import com.etnetera.hr.model.JavaScriptFramework
import com.etnetera.hr.repository.JavaScriptFrameworkRepository
import com.etnetera.hr.service.JavaScriptFrameworkServiceImpl
import spock.lang.Specification

/**
 * Tests service layer {@link JavaScriptFrameworkServiceImpl}.
 *
 * @author Jiri Grunwald
 */
class JavaScriptFrameworkServiceSpec extends Specification {

    def javaScriptFrameworkRepositoryMock = Mock(JavaScriptFrameworkRepository)
    def javaScriptFrameworkService = new JavaScriptFrameworkServiceImpl(javaScriptFrameworkRepositoryMock)

    def "Repository should be called and should contain two records"() {
        when:
        def frameworks = javaScriptFrameworkService.getJavaScriptFrameworks()

        then:
        frameworks != null
        frameworks.size() == 2
        1 * javaScriptFrameworkRepositoryMock.findAll() >> [new JavaScriptFramework(), new JavaScriptFramework()]
    }
}