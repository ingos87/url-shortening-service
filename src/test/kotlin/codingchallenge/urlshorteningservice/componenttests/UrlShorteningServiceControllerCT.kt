package codingchallenge.urlshorteningservice.componenttests

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.http.*


class UrlShorteningServiceControllerCT: UrlShorteningServiceSpecification() {

    @Test
    fun `api returns a valid value`() {
        val json = """{"url" : "https://www.i-love-you.com"}"""

        val response = createUrlIdentifier(json)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo("""{"url_identifier":"fb6b"}""")
    }

    @ParameterizedTest
    @CsvSource(
        """{}""",
        """{"uurl" : "https://www.i-love-you.com"}""",
        """{"URL" : "https://www.i-love-you.com"}""",
    )
    fun `api returns BAD_REQUEST on invalid json`(input: String) {
        val response = createUrlIdentifier(input)
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Disabled // fix later
    @Test
    fun `api returns BAD_REQUEST on too long url`() {
        val longDomain = "d".repeat(2050)
        val response = createUrlIdentifier("""{"url" : "https://www.${longDomain}.com"}""")
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }
}