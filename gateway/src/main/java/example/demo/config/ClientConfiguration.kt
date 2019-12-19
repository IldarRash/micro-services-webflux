package example.demo.config

import io.rsocket.RSocketFactory.ClientRSocketFactory
import io.rsocket.frame.decoder.PayloadDecoder
import io.rsocket.transport.netty.client.TcpClientTransport
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.util.MimeTypeUtils
import java.net.InetSocketAddress

@Configuration
class ClientConfiguration {
    private val logger = KotlinLogging.logger {}

    @Bean
    fun rSocketRequester(strategies: RSocketStrategies?): RSocketRequester? {
        val address = InetSocketAddress("127.0.0.1", 7000)
        logger.info("RSocket server address={}", address)
        return RSocketRequester.builder()
                .rsocketFactory { factory: ClientRSocketFactory ->
                    factory
                            .dataMimeType(MimeTypeUtils.ALL_VALUE)
                            .frameDecoder(PayloadDecoder.ZERO_COPY)
                }
                .rsocketStrategies(strategies)
                .connect(TcpClientTransport.create(address))
                .retry().block()
    }
}