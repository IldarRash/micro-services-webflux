package example.demo;

import auth.dto.AuthRequest;
import example.demo.client.AuthWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthComtroller {

    private final AuthWebService authWebService;

    @GetMapping("/auth")
    public Mono<AuthRequest> authRequestMono(String user) {
        return authWebService.auth(user);
    }

}
