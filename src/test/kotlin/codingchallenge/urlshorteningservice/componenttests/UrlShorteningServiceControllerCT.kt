package codingchallenge.urlshorteningservice.componenttests

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.http.HttpStatus


class UrlShorteningServiceControllerCT: UrlShorteningServiceSpecification() {

    @Test
    fun `api returns a valid value`() {
        val json = """{"url" : "https://www.i-love-you.com"}"""

        val response = createUrlIdentifier(json)

        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(response.body).isEqualTo("""{"url_identifier":"fb6b"}""")
    }

    @ParameterizedTest
    @CsvSource(
        """{}""",
        """{"uurl" : "https://www.i-love-you.com"}""",
        """{"URL" : "https://www.i-love-you.com"}""",
        """{"url" : null}""",
    )
    fun `api returns BAD_REQUEST on invalid json`(input: String) {
        val response = createUrlIdentifier(input)
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `api returns BAD_REQUEST on too long url`() {
        val longDomain = "d".repeat(2050)
        val response = createUrlIdentifier("""{"url" : "https://www.${longDomain}.com"}""")
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `api returns a valid value when retrieving a url by its identifier`() {
        val json = """{"url" : "https://www.i-love-you.com"}"""
        createUrlIdentifier(json) // persists fb6b

        val response = getUrlByIdentifier("fb6b")

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo("""{"url":"https://www.i-love-you.com"}""")
    }

    @Test
    fun `api returns BAD_REQUEST when trying to retrieve a url using an invalid identifier`() {
        val response = getUrlByIdentifier("3213213132132132131313213213213213213213213213213132131")
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `api returns not found if url mapping is not present`() {
        val response = getUrlByIdentifier("none")
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }
}