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

        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)

        val dbContent = urlIdentifierMappingRepository.findAll()
        assertThat(dbContent.size).isEqualTo(1)
        assertThat(dbContent[0].urlIdentifier).isEqualTo("fb6b")
        assertThat(dbContent[0].url).isEqualTo("https://www.i-love-you.com")
    }

    @Test
    fun `api will not persist duplicate mappings`() {
        val response = createUrlIdentifier("""{"url" : "https://www.i-love-you.com"}""")
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)

        val response2 = createUrlIdentifier("""{"url" : "https://www.i-love-you.com"}""")
        assertThat(response2.statusCode).isEqualTo(HttpStatus.CREATED)

        val dbContent = urlIdentifierMappingRepository.findAll()
        assertThat(dbContent.size).isEqualTo(1)
        assertThat(dbContent[0].urlIdentifier).isEqualTo("fb6b")
        assertThat(dbContent[0].url).isEqualTo("https://www.i-love-you.com")
    }

    @Test
    fun `api will persist different mapping if identifier is already taken by another url`() {
        createUrlIdentifier("""{"url" : "1081"}""") // 36a16a2505369e0c922b6ea7a23a56d2
        createUrlIdentifier("""{"url" : "1172"}""") // 36a1694bce9815b7e38a9dad05ad42e0

        val dbContent = urlIdentifierMappingRepository.findAll()
        assertThat(dbContent.size).isEqualTo(2)
        assertThat(dbContent[0].urlIdentifier).isEqualTo("36a1")
        assertThat(dbContent[0].url).isEqualTo("1081")
        assertThat(dbContent[1].urlIdentifier).startsWith("36a1")
        assertThat(dbContent[1].urlIdentifier.length).isGreaterThan(4)
        assertThat(dbContent[1].url).isEqualTo("1172")
    }
}