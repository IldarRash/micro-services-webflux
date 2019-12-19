package example.demo.filter

import auth.dto.AuthRequest
import example.demo.client.AuthWebService
import mu.KotlinLogging
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AutherizationGatewayFilterFactory(val authWebService: AuthWebService) : AbstractGatewayFilterFactory<Any?>() {
    private val logger = KotlinLogging.logger {}

    override fun apply(config: Any?): GatewayFilter {
        return GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            logger.info("Auth filter request")
            authRequest(exchange).flatMap { authRequest: AuthRequest ->
                logger.info("authorization redirect {}", if (authRequest.auth) "not required" else "required")
                if (authRequest.auth) chain.filter(exchange) else redirectToAutPage(exchange)
            }
        }
    }

    private fun authRequest(exchange: ServerWebExchange): Mono<AuthRequest> {
        return authWebService.auth(exchange.request.queryParams.toSingleValueMap()["user"])
    }

    private fun redirectToAutPage(exchange: ServerWebExchange): Mono<Void> {
        logger.info("send redirect to authorization page")
        return Mono.fromRunnable {
            val response = exchange.response
            response.statusCode = HttpStatus.FOUND
        }
    }
}