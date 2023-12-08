package codingchallenge.urlshorteningservice.controller

import codingchallenge.urlshorteningservice.persistence.UrlIdentifierMappingRepository
import codingchallenge.urlshorteningservice.service.PersistUrlIdentifierService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.beans.factory.parsing.Problem
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UrlShorteningServiceController(
    val persistUrlIdentifierService: PersistUrlIdentifierService,
    val urlIdentifierMappingRepository: UrlIdentifierMappingRepository,
) {

    @Operation(
        summary = "Receives a single string (e.g. an URL), creates a hash and saves both." +
                "The identifier (hash) is returned, enabling the user to retrieve the full URL later.",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "CREATED, when data was persisted and resulting identifier returned",
            ), ApiResponse(responseCode = "400", description = "Bad Request", content = [Content(schema = Schema(implementation = Problem::class))]),
        ],
    )
    @PostMapping("/url", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun persistUrlMapping(@RequestBody @Valid request: UrlVO): ResponseEntity<UrlIdentifierVO> {
        // bean validation simply doesn't work
        if (request.url.length > 2048) {
            return ResponseEntity.badRequest().build()
        }
        val identifier = persistUrlIdentifierService.persistUrlIdentifierMapping(request.url)
        return ResponseEntity.ok().body(UrlIdentifierVO(urlIdentifier = identifier))
    }

    @Operation(
        summary = "Returns the previously deposited url according to the given identifier.",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "OK, when identifier was found. The full URL is returned",
            ),
            ApiResponse(
                responseCode = "404",
                description = "Not found, when identifier was not found",
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad Request",
                content = [Content(schema = Schema(implementation = Problem::class))]
            ),
        ],
    )
    @GetMapping("/url/{identifier}")
    fun getUrlByIdentifier(@PathVariable @Valid identifier: String?): ResponseEntity<UrlVO> {
        // bean validation simply doesn't work
        if (identifier == null || identifier.length > 32) {
            return ResponseEntity.badRequest().build()
        }
        val fromDb = urlIdentifierMappingRepository.findByUrlIdentifier(identifier)
        return if (fromDb.size == 1) {
            ResponseEntity.ok(UrlVO(url = fromDb[0].url))
        } else {
            ResponseEntity.notFound().build()
        }
    }
}