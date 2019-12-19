package example.demo.client;

import com.netflix.discovery.EurekaClient;
import auth.dto.AuthRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthWebService {

    private final EurekaClient discoveryClient;
    private final RSocketRequester rSocketRequestert;

    public Mono<AuthRequest> auth(String user) {

        log.info("Get request {}, {}", user);
        return rSocketRequestert.route("auth-handler")
                .data(user)
                .retrieveMono(AuthRequest.class);


    }
}
