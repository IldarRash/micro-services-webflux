package example.demo.configs

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories
class MongoConfig : AbstractReactiveMongoConfiguration() {

    override fun getDatabaseName() = "openid"

    override fun reactiveMongoClient() = mongoClient()

    @Bean
    fun mongoClient() = MongoClients
            .create(mongoConnectionSettings())

    fun mongoConnectionSettings () = MongoClientSettings.builder()
            .credential(MongoCredential.createCredential("root", "admin", "example".toCharArray()))
            .build();

    @Bean
    override fun reactiveMongoTemplate()
            = ReactiveMongoTemplate(mongoClient(), databaseName)
}