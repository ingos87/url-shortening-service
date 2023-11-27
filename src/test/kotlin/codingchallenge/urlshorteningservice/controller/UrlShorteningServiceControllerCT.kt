package codingchallenge.urlshorteningservice.controller

import codingchallenge.urlshorteningservice.UrlShorteningServiceSpecification
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.*


class UrlShorteningServiceControllerCT: UrlShorteningServiceSpecification() {

    @Test
    fun `api returns a valid value`() {
        val json = """{"url" : "https://www.i-love-you.com"}"""

        val response = createUrlIdentifier(json)

        assertThat(response.statusCode).isEqualTo(HttpStatusCode.valueOf(200))
        assertThat(response.body).isEqualTo("""{"url_identifier":"hello world"}""")
    }
}