package codingchallenge.urlshorteningservice.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(name = "URL_IDENTIFIER_MAPPING")
class UrlIdentifierMapping(

    @Column(name = "URL_IDENTIFIER", length = 32, nullable = false, unique=true)
    val urlIdentifier: String,

    @Column(name = "URL", length = 2048, nullable = false)
    val url: String,

) : AbstractVersionableEntity()
