package auth.dto

interface Request {}

data class AuthRequest(val auth: Boolean, val token: String?) : Request

class UserInfo(

) : Request

