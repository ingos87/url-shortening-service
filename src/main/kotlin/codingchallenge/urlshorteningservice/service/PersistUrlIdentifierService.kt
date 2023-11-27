package codingchallenge.urlshorteningservice.service

import codingchallenge.urlshorteningservice.persistence.UrlIdentifierMapping
import codingchallenge.urlshorteningservice.persistence.UrlIdentifierMappingRepository
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Service

@Service
class PersistUrlIdentifierService(
    val urlIdentifierMappingRepository: UrlIdentifierMappingRepository,
    val urlIdentifierCalculator: UrlIdentifierCalculator,
) {

    fun persistUrlIdentifierMapping(url: String): String {
        var identifier = urlIdentifierCalculator.calculateIdentifier(url)

        val maybeMapping = urlIdentifierMappingRepository.findByUrlIdentifier(identifier)
        if (maybeMapping.isNotEmpty()) {
            if (maybeMapping[0].url == url) {
                return identifier
            }
            do {
                // TODO monitor this in the future. We need to rethink if this generates too many db reads
                identifier += RandomStringUtils.randomAlphanumeric(1).lowercase()
            } while(isIdentifierTaken(identifier))
        }

        urlIdentifierMappingRepository.save(UrlIdentifierMapping(identifier, url))
        return identifier
    }

    private fun isIdentifierTaken(urlIdentifier: String): Boolean {
        return urlIdentifierMappingRepository.findByUrlIdentifier(urlIdentifier).isNotEmpty()
    }

}