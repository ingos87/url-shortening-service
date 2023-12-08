package codingchallenge.urlshorteningservice.service

import codingchallenge.urlshorteningservice.persistence.UrlIdentifierMapping
import codingchallenge.urlshorteningservice.persistence.UrlIdentifierMappingRepository
import jakarta.persistence.UniqueConstraint
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class PersistUrlIdentifierService(
    val urlIdentifierMappingRepository: UrlIdentifierMappingRepository,
    val urlIdentifierCalculator: UrlIdentifierCalculator,
) {

    fun persistUrlIdentifierMapping(url: String, identifierSuffix: String = ""): String {
        val identifier = urlIdentifierCalculator.calculateIdentifier(url) + identifierSuffix
        try {
            urlIdentifierMappingRepository.save(UrlIdentifierMapping(identifier, url))
        } catch (e: DataIntegrityViolationException) {
            val maybeMapping = urlIdentifierMappingRepository.findByUrlIdentifier(identifier)
            return if (maybeMapping.isNotEmpty() && maybeMapping[0].url == url) {
                identifier
            } else {
                persistUrlIdentifierMapping(url, RandomStringUtils.randomAlphanumeric(1).lowercase())
            }
        }

        return identifier
    }
}