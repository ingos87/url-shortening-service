package codingchallenge.urlshorteningservice

import codingchallenge.urlshorteningservice.service.UrlIdentifierCalculator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class UrlIdentifierCalculatorTests {

	companion object {
		@JvmStatic
		fun mappings() = listOf(
			Arguments.of("", "d41d"),
			Arguments.of("https://${"d".repeat(2036)}.com", "4289"),
			Arguments.of("this_is_no_url_at_all", "4788"),
		)
	}

	@ParameterizedTest
	@MethodSource("mappings")
	fun shouldCalculateIdentifier(urlInput: String, expectedUrlIdentifier: String) {
		val identifier = UrlIdentifierCalculator().calculateIdentifier(urlInput)
		assertThat(identifier).isEqualTo(expectedUrlIdentifier)
	}

}
