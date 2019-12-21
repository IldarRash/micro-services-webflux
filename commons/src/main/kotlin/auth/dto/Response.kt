package auth.dto

interface Response {
}

class AuthResponse(val auth: Boolean, val accessToken: String? = "", val refreshToken: String? = "") : Response {

}

class CodeResponse(val auth: Boolean, val authCode: String?) : Response {

}