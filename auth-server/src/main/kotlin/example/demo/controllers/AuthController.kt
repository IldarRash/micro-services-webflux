package example.demo.controllers;

import auth.dto.AuthRequest
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import java.sql.DriverManager.println

@Controller
class AuthController {

    @MessageMapping("auth-handler")
    suspend fun auth(user: String): AuthRequest {
        println("User log with this userName");
        return if ("user" == user) AuthRequest(true) else AuthRequest(false)
    }
}
