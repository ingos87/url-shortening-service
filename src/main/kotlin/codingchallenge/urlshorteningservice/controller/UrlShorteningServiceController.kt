package codingchallenge.urlshorteningservice.controller

import codingchallenge.urlshorteningservice.persistence.UrlIdentifierMappingRepository
import codingchallenge.urlshorteningservice.service.PersistUrlIdentifierService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UrlShorteningServiceController(
    val persistUrlIdentifierService: PersistUrlIdentifierService,
    val urlIdentifierMappingRepository: UrlIdentifierMappingRepository,
) {

    @PostMapping("/create-url-identifier", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun persistUrlMapping(@RequestBody @Valid request: UrlVO): ResponseEntity<UrlIdentifierVO> {
        val identifier = persistUrlIdentifierService.persistUrlIdentifierMapping(request.url)
        return ResponseEntity.ok().body(UrlIdentifierVO(urlIdentifier = identifier))
    }

    @GetMapping("/get-url/{identifier}")
    fun getUrlByIdentifier(@PathVariable identifier: String): ResponseEntity<UrlVO> {
        val fromDb = urlIdentifierMappingRepository.findByUrlIdentifier(identifier)
        if (fromDb.size == 1) {
            return ResponseEntity.ok().body(UrlVO(url = fromDb[0].url))
        }
        return ResponseEntity.notFound().build()
    }
}