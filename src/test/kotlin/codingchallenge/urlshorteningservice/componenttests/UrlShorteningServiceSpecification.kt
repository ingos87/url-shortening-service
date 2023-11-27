package codingchallenge.urlshorteningservice.componenttests

import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import java.net.URI


@ActiveProfiles(value = ["componenttest"])
@AutoConfigureObservability
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
)
class UrlShorteningServiceSpecification {

    private var restTemplate: RestTemplate = RestTemplate()

    fun createUrlIdentifier(body: String): ResponseEntity<String> {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        val httpEntity = HttpEntity(
            body,
            httpHeaders,
        )
        val uri = URI.create("http://localhost:8090/create-url-identifier")

        return try {
            return restTemplate.postForEntity<String>(uri, httpEntity)
        } catch (e: HttpStatusCodeException) {
            ResponseEntity.status(e.statusCode).headers(e.responseHeaders)
                .body(e.responseBodyAsString)
        }
    }

    fun getUrlByIdentifier(identifier: String): ResponseEntity<String> {
        return restTemplate.getForEntity("http://localhost:8090/get-url/$identifier", String::class.java)
    }
}