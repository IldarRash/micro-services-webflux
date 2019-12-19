package example.demo.client

import auth.dto.AuthRequest
import mu.KotlinLogging
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthWebService(val rSocketRequester: RSocketRequester) {
    private val logger = KotlinLogging.logger {}

    fun auth(user: String?): Mono<AuthRequest> {
        logger.info("Get request {}, {}", user)
        return rSocketRequester.route("auth-handler")
                .data(user!!)
                .retrieveMono(AuthRequest::class.java)
    }
}