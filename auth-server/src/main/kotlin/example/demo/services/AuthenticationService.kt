package example.demo.services

import auth.dto.AuthRequest
import auth.dto.CodeResponse
import example.demo.models.Session
import example.demo.repositories.SessionRepo
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.*


@Service
class AuthenticationService (val sessionRepo: SessionRepo) {

    suspend fun creatSessionAndGetAuthCode(authRequest: AuthRequest): CodeResponse {
        val uuid = UUID.randomUUID();
        val session = sessionRepo.saveSession(Session(uuid, getUserId(authRequest), uuid.toString(), uuid.toString(), uuid.toString()))
        return CodeResponse(true, session.authCode)
    }


    private fun getUserId(authRequest: AuthRequest): String {
        val base64Credentials: String? = authRequest.token?.substring("Basic".length)?.trim()
        val credDecoded = Base64.getDecoder().decode(base64Credentials)
        val credentials = String(credDecoded, StandardCharsets.UTF_8)
        // credentials = username:password
        // credentials = username:password
        val values = credentials.split(":".toRegex(), 2)
        return values[0];
    }
}