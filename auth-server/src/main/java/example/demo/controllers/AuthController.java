package example.demo.controllers;

import auth.dto.AuthRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class AuthController {

    @MessageMapping("auth-handler")
    public Mono<AuthRequest> auth(String user) {
        log.info("User log with this userName {}", user);
        return "user".equals(user) ? Mono.just(new AuthRequest(true)) : Mono.just(new AuthRequest(false));
    }
}
