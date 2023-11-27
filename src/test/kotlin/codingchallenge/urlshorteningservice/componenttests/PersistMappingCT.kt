package codingchallenge.urlshorteningservice.componenttests

import codingchallenge.urlshorteningservice.persistence.UrlIdentifierMappingRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

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
}