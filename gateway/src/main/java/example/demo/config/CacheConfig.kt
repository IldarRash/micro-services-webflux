package example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession


@Configuration
@EnableRedisWebSession
class CacheConfig {

    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory {
        return LettuceConnectionFactory("localhost", 6379)
    }

}