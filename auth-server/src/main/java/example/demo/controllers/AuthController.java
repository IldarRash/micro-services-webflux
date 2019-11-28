package example.demo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class AuthController {

    @RequestMapping("/auth")
    public Mono<Boolean> auth(@RequestParam String user) {
        log.info("User log with this userName", user);
        return "user".equals(user) ? Mono.just(true) : Mono.just(false);
    }
}
