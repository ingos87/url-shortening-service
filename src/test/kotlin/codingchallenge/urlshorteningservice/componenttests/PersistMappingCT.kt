package codingchallenge.urlshorteningservice.componenttests

import codingchallenge.urlshorteningservice.persistence.UrlIdentifierMappingRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

class PersistMappingCT @Autowired constructor(
    val urlIdentifierMappingRepository: UrlIdentifierMappingRepository
): UrlShorteningServiceSpecification() {

    @BeforeEach
    fun flushDb() {
        urlIdentifierMappingRepository.deleteAll()
    }

    @Test
    fun `api persists identifier and url`() {
        val json = """{"url" : "https://www.i-love-you.com"}"""

        val response = createUrlIdentifier(json)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        val dbContent = urlIdentifierMappingRepository.findAll()
        assertThat(dbContent.size).isEqualTo(1)
        assertThat(dbContent[0].urlIdentifier).isEqualTo("fb6b")
        assertThat(dbContent[0].url).isEqualTo("https://www.i-love-you.com")
    }

    @Test
    fun `api will not persist duplicate mappings`() {
        val response = createUrlIdentifier("""{"url" : "https://www.i-love-you.com"}""")
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        val response2 = createUrlIdentifier("""{"url" : "https://www.i-love-you.com"}""")
        assertThat(response2.statusCode).isEqualTo(HttpStatus.OK)

        val dbContent = urlIdentifierMappingRepository.findAll()
        assertThat(dbContent.size).isEqualTo(1)
        assertThat(dbContent[0].urlIdentifier).isEqualTo("fb6b")
        assertThat(dbContent[0].url).isEqualTo("https://www.i-love-you.com")
    }
}