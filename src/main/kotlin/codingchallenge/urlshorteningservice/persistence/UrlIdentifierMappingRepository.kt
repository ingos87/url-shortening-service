package codingchallenge.urlshorteningservice.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface UrlIdentifierMappingRepository : JpaRepository<UrlIdentifierMapping, String>
