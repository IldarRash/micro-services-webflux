package auth.dto

interface Request {}

class AuthRequest (val auth: Boolean) : Request

class UserInfo(

) : Request

