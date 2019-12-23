package example.demo.filter

import auth.dto.AuthRequest
import auth.dto.AuthResponse
import auth.dto.Response
import example.demo.client.AuthorizationService
import example.demo.model.AuthContext
import mu.KotlinLogging
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono
import java.util.*

@Component
class AuthorizationGatewayFilterFactory(val authorizationService: AuthorizationService) : AbstractGatewayFilterFactory<Any?>() {
    private val logger = KotlinLogging.logger {}

    override fun apply(config: Any?): GatewayFilter {
        return GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            logger.info("Auth filter request")
            authRequest(exchange).flatMap { authResponse : AuthResponse ->
                logger.info("authorization redirect {}", if (authResponse.auth) "not required" else "required")
                if (authResponse.auth) chain.filter(exchange) else redirectToAutPage(exchange)
            }
        }
    }

    private fun authRequest(exchange: ServerWebExchange): Mono<AuthResponse> {
        return exchange.session
                .flatMap {
                    val authContext = it.getAttribute<AuthContext>("auth")
                    return@flatMap if (authContext == null)
                        createInitRequest(it)
                    else {
                        authorizationService.getAuthCode(AuthRequest(authContext.userId));
                    }
                }
    }

    private fun redirectToAutPage(exchange: ServerWebExchange): Mono<Void> {
        logger.info("send redirect to authorization page")
        return Mono.fromRunnable {
            val response = exchange.response
            response.statusCode = HttpStatus.FOUND
        }
    }

    private fun createInitRequest(webSession: WebSession): Mono<AuthResponse> {
        webSession.attributes["auth"] = AuthContext(UUID.randomUUID().toString())
        return Mono.just(AuthResponse(false))

    }
}