package example.demo.repositories

import example.demo.models.Session
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitLast
import mu.KotlinLogging
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository

@Repository
class SessionRepo (val reactiveMongoTemplate: ReactiveMongoTemplate) {
    private val logger = KotlinLogging.logger {}

    suspend fun getSessions(): Session {
        return reactiveMongoTemplate.findAll(Session::class.java).awaitFirst()
    }

    suspend fun saveSession(session: Session): Session {
        return reactiveMongoTemplate.save(session).awaitLast()
    }
}