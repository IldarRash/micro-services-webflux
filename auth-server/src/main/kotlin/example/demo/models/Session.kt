package example.demo.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "session")
class Session(@Id val id : UUID, val user : String){
    override fun toString(): String {
        return "Session(id='$id', user='$user')"
    }
}
