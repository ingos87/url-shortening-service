package codingchallenge.urlshorteningservice.service

import codingchallenge.urlshorteningservice.persistence.UrlIdentifierMapping
import codingchallenge.urlshorteningservice.persistence.UrlIdentifierMappingRepository
import org.springframework.stereotype.Service

@Service
class PersistUrlIdentifierService(
    val urlIdentifierMappingRepository: UrlIdentifierMappingRepository,
    val urlIdentifierCalculator: UrlIdentifierCalculator,
) {

    fun persistUrlIdentifierMapping(url: String): String {
        val identifier = urlIdentifierCalculator.calculateIdentifier(url)
        urlIdentifierMappingRepository.save(UrlIdentifierMapping(identifier, url))
        return identifier
    }

}