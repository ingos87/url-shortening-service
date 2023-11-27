package codingchallenge.urlshorteningservice.service

import org.springframework.stereotype.Service
import java.math.BigInteger
import java.security.MessageDigest

@Service
class UrlIdentifierCalculator {

    fun calculateIdentifier(url: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(url.toByteArray())).toString(16).take(4)
    }
}
