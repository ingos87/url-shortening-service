package codingchallenge.urlshorteningservice.persistence

import org.springframework.data.jpa.repository.JpaRepository

// this is basically a local (H2 database) cache. IF we want horizontal scaling, we need some
// mechanism to outsource this data or sync the different nodes' caches.
interface UrlIdentifierMappingRepository : JpaRepository<UrlIdentifierMapping, String> {
    fun findByUrlIdentifier(urlIdentifier: String): List<UrlIdentifierMapping>
}
