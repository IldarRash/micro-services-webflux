package example.demo.filter;

import auth.dto.AuthRequest;
import example.demo.client.AuthWebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class AutherizationGatewayFilterFactory extends AbstractGatewayFilterFactory {
    private final AuthWebService authWebService;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            log.info("Auth filter request");
            return authRequest(exchange).flatMap(authRequest -> {
                        log.info("authorization redirect {}", authRequest.isAuth() ? "not required" : "required");
                        return authRequest.isAuth() ? chain.filter(exchange) : redirectToAutPage(exchange);
                    }
            );
        };
    }

    private Mono<AuthRequest> authRequest(ServerWebExchange exchange) {
        return authWebService.auth(exchange.getRequest().getQueryParams().get("user").get(0));

    }

    private Mono<Void> redirectToAutPage(ServerWebExchange exchange) {
        log.info("send redirect to authorization page");

        return Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FOUND);
        });
    }

}
