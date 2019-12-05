package example.demo.manager;

import example.demo.client.AuthWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReactiveAuthManager implements ReactiveAuthenticationManager {
    private final AuthWebService authWebService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        Mono<Authentication> authenticationMono = authWebService.auth("user1")
                .flatMap(authRequest -> Mono.just(new UsernamePasswordAuthenticationToken("User", "password")));
        return authenticationMono;
    }
}
