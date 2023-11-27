package codingchallenge.urlshorteningservice.controller

import codingchallenge.urlshorteningservice.UrlShorteningServiceSpecification
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.http.*


class UrlShorteningServiceControllerCT: UrlShorteningServiceSpecification() {

    @Test
    fun `api returns a valid value`() {
        val json = """{"url" : "https://www.i-love-you.com"}"""

        val response = createUrlIdentifier(json)

        assertThat(response.statusCode).isEqualTo(HttpStatusCode.valueOf(200))
        assertThat(response.body).isEqualTo("""{"url_identifier":"hello world"}""")
    }

    @ParameterizedTest
    @CsvSource(
        """{}""",
        """{"uurl" : "https://www.i-love-you.com"}""",
        """{"URL" : "https://www.i-love-you.com"}""",
    )
    fun `api returns BAD_REQUEST on invalid json`(input: String) {
        val response = createUrlIdentifier(input)
        assertThat(response.statusCode).isEqualTo(HttpStatusCode.valueOf(400))
    }

    @Disabled // fix later
    @Test
    fun `api returns BAD_REQUEST on too long url`() {
        val longDomain = "d".repeat(2050)
        val response = createUrlIdentifier("""{"url" : "https://www.${longDomain}.com"}""")
        assertThat(response.statusCode).isEqualTo(HttpStatusCode.valueOf(400))
    }
}