package example.demo.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("session")
class Session(@Id val id : String, val user : String)