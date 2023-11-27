package codingchallenge.urlshorteningservice.controller

import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UrlShorteningServiceController {

    @PostMapping("/create-url-identifier", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun persistUrlMapping(@RequestBody @Valid request: UrlVO): ResponseEntity<UrlIdentifierVO> {
        return ResponseEntity.ok().body(UrlIdentifierVO(urlIdentifier = "hello world"))
    }
}