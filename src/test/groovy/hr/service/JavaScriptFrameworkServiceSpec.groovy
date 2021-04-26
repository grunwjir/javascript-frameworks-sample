package hr.service

import com.etnetera.hr.data.JavaScriptFrameworkIn
import com.etnetera.hr.model.HypeLevel
import com.etnetera.hr.model.JavaScriptFramework
import com.etnetera.hr.model.JavaScriptFrameworkNotFoundException
import com.etnetera.hr.model.JavaScriptFrameworkVersion
import com.etnetera.hr.repository.JavaScriptFrameworkRepository
import com.etnetera.hr.service.JavaScriptFrameworkServiceImpl
import spock.lang.Specification

import java.time.LocalDate

/**
 * Tests service layer {@link JavaScriptFrameworkServiceImpl}.
 *
 * @author Jiri Grunwald
 */
class JavaScriptFrameworkServiceSpec extends Specification {

    def javaScriptFrameworkRepositoryMock = Mock(JavaScriptFrameworkRepository)
    def javaScriptFrameworkService = new JavaScriptFrameworkServiceImpl(javaScriptFrameworkRepositoryMock)

    def "Repository should be called and should contain two records - getJavaScriptFrameworks"() {
        when:
        def frameworks = javaScriptFrameworkService.getJavaScriptFrameworks()

        then:
        frameworks != null
        frameworks.size() == 2
        1 * javaScriptFrameworkRepositoryMock.findAll() >> [new JavaScriptFramework(), new JavaScriptFramework()]
    }

    def "New JS framework should be saved"() {
        given:
        def versions = [new JavaScriptFrameworkIn.JavaScriptFrameworkVersionIn(version: "15.5",
                deprecationDate: LocalDate.of(2021, 12, 15), hypeLevel: HypeLevel.NORMAL)]
        def framework = new JavaScriptFrameworkIn(name: "Angular", versions: versions)

        when:
        def created = javaScriptFrameworkService.createJavascriptFramework(framework)

        then:
        created.getId() == 5
        // verify that mapping from input object to domain works well
        1 * javaScriptFrameworkRepositoryMock.save(new JavaScriptFramework(name: "Angular",
                versions: [new JavaScriptFrameworkVersion(version: "15.5",
                        deprecationDate: LocalDate.of(2021, 12, 15),
                        hypeLevel: HypeLevel.NORMAL)])
        ) >> new JavaScriptFramework(id: 5)
    }

    def "Existing JS framework should be updated"() {
        given:
        def versions = [new JavaScriptFrameworkIn.JavaScriptFrameworkVersionIn(version: "15.5",
                deprecationDate: LocalDate.of(2021, 12, 15), hypeLevel: HypeLevel.NORMAL)]
        def framework = new JavaScriptFrameworkIn(id: 3, name: "Angular", versions: versions)

        when:
        def updated = javaScriptFrameworkService.updateJavascriptFramework(framework)

        then:
        updated.getId() == 3
        // verify that mapping from input object to domain works well
        1 * javaScriptFrameworkRepositoryMock.save(new JavaScriptFramework(id: 3, name: "Angular",
                versions: [new JavaScriptFrameworkVersion(version: "15.5",
                        deprecationDate: LocalDate.of(2021, 12, 15),
                        hypeLevel: HypeLevel.NORMAL)])
        ) >> new JavaScriptFramework(id: 3)
        //
        1 * javaScriptFrameworkRepositoryMock.existsById(3) >> true
    }

    def "Non-existing JS framework shouldn't be updated"() {
        given:
        def framework = new JavaScriptFrameworkIn(id: 3, name: "Angular", versions: [])

        when:
        javaScriptFrameworkService.updateJavascriptFramework(framework)

        then:
        // shouldn't be saved
        0 * javaScriptFrameworkRepositoryMock.save(_)
        // not exists
        1 * javaScriptFrameworkRepositoryMock.existsById(3) >> false

        and: "Exception should be thrown"
        thrown(JavaScriptFrameworkNotFoundException)
    }

    def "Existing JS framework should be deleted"() {
        when:
        javaScriptFrameworkService.deleteJavascriptFramework(5)

        then:
        1 * javaScriptFrameworkRepositoryMock.deleteById(5)
        //
        1 * javaScriptFrameworkRepositoryMock.existsById(5) >> true
    }

    def "Non-existing JS framework shouldn't be deleted"() {
        when:
        javaScriptFrameworkService.deleteJavascriptFramework(5)

        then:
        0 * javaScriptFrameworkRepositoryMock.deleteById(_)
        //
        1 * javaScriptFrameworkRepositoryMock.existsById(5) >> false

        and: "Exception should be thrown"
        thrown(JavaScriptFrameworkNotFoundException)
    }

}