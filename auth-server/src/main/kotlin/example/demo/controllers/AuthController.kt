package example.demo.controllers;

import auth.dto.AuthRequest
import auth.dto.AuthResponse
import auth.dto.Request
import mu.KotlinLogging
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import java.sql.DriverManager.println

@Controller
class AuthController {
    private val logger = KotlinLogging.logger {}

    @MessageMapping("auth-handler")
    suspend fun auth(user: String): AuthRequest {
        logger.info { "User log with this userName ${user}"};
        return if ("user" == user) AuthRequest(true) else AuthRequest(false)
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
