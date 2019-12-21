package example.demo.controllers;

import auth.dto.AuthRequest
import auth.dto.AuthResponse
import auth.dto.CodeResponse
import example.demo.services.AuthenticationService
import mu.KotlinLogging
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class AuthController(val authenticationService: AuthenticationService) {
    private val logger = KotlinLogging.logger {}

    @MessageMapping("get-auth-code")
    suspend fun getAuthCode(request: AuthRequest) : CodeResponse {
        logger.info { "get auth code by request $request" }
        return authenticationService.creatSessionAndGetAuthCode(request);
    }

    @MessageMapping("get-tokens")
    suspend fun getTokensByAuthCode(request: AuthRequest) {
        logger.info { "get token by auth code $request" }
        //return AuthResponse(true)
    }
}
