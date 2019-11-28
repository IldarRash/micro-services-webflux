package example.demo.filter;

import example.demo.client.AuthWebService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import static example.demo.filter.AuthFilter.*;

@Component
public class AuthFilter implements GatewayFilterFactory<Config> {

    @Autowired
    private AuthWebService authWebService;

    @Override
    public Config newConfig() {
        return new Config();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            authWebService.auth(exchange.getRequest().getQueryParams().get("user").get(0));
            return chain.filter(exchange);
        });
    }

    @Data
    public static class Config {
        private String name = "test";
    }
}
