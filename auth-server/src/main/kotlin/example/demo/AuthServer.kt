package example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient


@EnableDiscoveryClient
@SpringBootApplication(exclude = [MongoReactiveDataAutoConfiguration::class])
class AuthServer

fun main(args: Array<String>) {
    runApplication<AuthServer>(*args)
}