package example.demo.filter

import auth.dto.AuthRequest
import auth.dto.AuthResponse
import auth.dto.Response
import example.demo.client.AuthorizationService
import mu.KotlinLogging
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

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
        if (exchange.request.headers.containsKey("authorization"))
            return authorizationService.getAuthCode(AuthRequest((exchange.request.headers["authorization"]?.get(0))));
        return Mono.just(AuthResponse(false))
    }

    private fun redirectToAutPage(exchange: ServerWebExchange): Mono<Void> {
        logger.info("send redirect to authorization page")
        return Mono.fromRunnable {
            val response = exchange.response
            response.statusCode = HttpStatus.FOUND
        }
    }
}