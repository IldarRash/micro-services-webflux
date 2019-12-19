package example.demo.controllers;

import auth.dto.AuthRequest
import auth.dto.AuthResponse
import example.demo.models.Session
import example.demo.repositories.SessionRepo
import mu.KotlinLogging
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class AuthController(val sessionRepo: SessionRepo) {
    private val logger = KotlinLogging.logger {}

    @MessageMapping("auth-handler")
    suspend fun auth(user: String): AuthRequest {
        logger.info { "User log with this userName ${user}"};
        if ("user" == user) {
            val usrSession = sessionRepo.saveSession(Session(UUID.randomUUID(), user))
            return AuthRequest(true, usrSession.id.toString())
        }
        return  AuthRequest(false, null)
    }


    @MessageMapping("get-auth-code")
    suspend fun getAuthCode(request: AuthRequest) : AuthResponse {
        logger.info { "get auth code by request $request" }
        return AuthResponse(true);
    }

    @MessageMapping("get-tokens")
    suspend fun getTokensByAuthCode(request: AuthRequest) : AuthResponse {
        logger.info { "get token by auth code $request" }
        return AuthResponse(true);
    }
}
