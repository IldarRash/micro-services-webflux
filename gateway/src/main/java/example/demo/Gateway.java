package example.demo;

import example.demo.config.UriConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@SpringBootApplication
@EnableWebFlux
@EnableEurekaClient
@EnableConfigurationProperties(UriConfiguration.class)
@RestController
public class Gateway {

    public static void main(String[] args) {
        SpringApplication.run(Gateway.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String httpUri = uriConfiguration.getHttpbin();
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .uri(httpUri))
                .build();
    }

    /*@Bean
    public RouterFunction authRoute() {
        return RouterFunctions
                .route(POST("/auth/login").and(accept(APPLICATION_JSON)), authHandler::login)
                .andRoute(POST("/auth/signup").and(accept(APPLICATION_JSON)), authHandler::signUp);
    }*/
}
