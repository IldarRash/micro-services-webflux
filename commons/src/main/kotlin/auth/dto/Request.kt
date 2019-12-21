package auth.dto

interface Request {}

data class AuthRequest(val token: String?) : Request

class UserInfo(

) : Request

