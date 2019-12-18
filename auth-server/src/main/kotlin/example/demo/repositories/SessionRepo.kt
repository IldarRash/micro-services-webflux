package example.demo.repositories

import example.demo.models.Session
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct

@Repository
class SessionRepo (val reactiveMongoTemplate: ReactiveMongoTemplate) {

    suspend fun getSessions(): Session? {
        return reactiveMongoTemplate.findAll(Session::class.java).awaitFirst()
    }

    suspend fun saveSession(session: Session) {
        reactiveMongoTemplate.save(session)
    }

    @PostConstruct
    fun init() {
        for (i in 1..10) {
            reactiveMongoTemplate.save(Session(i.toString(), "user$i")).log();
        }
    }
}