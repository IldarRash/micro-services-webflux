package example.demo.repositories

import example.demo.models.Session
import kotlinx.coroutines.reactive.awaitFirst
import mu.KotlinLogging
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import javax.annotation.PostConstruct

@Repository
class SessionRepo (val reactiveMongoTemplate: ReactiveMongoTemplate) {
    private val logger = KotlinLogging.logger {}

    suspend fun getSessions(): Session? {
        return reactiveMongoTemplate.findAll(Session::class.java).awaitFirst()
    }

    fun saveSession(session: Session): Mono<Session> {
        return reactiveMongoTemplate.save(session)
    }

    @PostConstruct
    fun init() {
        val sessions = Flux
                .range(1, 10)
                .flatMap { it -> saveSession(Session(UUID.randomUUID(), "user$it")) }
                .doOnEach { it -> logger.info { "Session = $it" } }
                .subscribe()
        logger.info { sessions }
    }
}