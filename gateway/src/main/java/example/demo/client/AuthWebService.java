package example.demo.client;

import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthWebService {

    private final EurekaClient discoveryClient;
    private final WebClient client;

    public Mono<Boolean> auth(String user) {
        return client.get()
                .uri("/auth")
                .attribute("user", user)
                .exchange()
                .cast(Boolean.class);


    }
}
