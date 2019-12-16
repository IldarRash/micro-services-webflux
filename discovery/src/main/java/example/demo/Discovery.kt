package example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaServer
class Discovery


@ConditionalOnMissingBean
@Bean
fun httpTraceRepository() : HttpTraceRepository {
    return InMemoryHttpTraceRepository()
}

fun main(args: Array<String>) {
    runApplication<Discovery>(*args)
}
