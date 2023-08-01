package org.example.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import static io.netty.handler.logging.LogLevel.DEBUG;
import static org.springframework.web.reactive.function.client.WebClient.builder;
import static reactor.netty.http.client.HttpClient.create;
import static reactor.netty.transport.logging.AdvancedByteBufFormat.TEXTUAL;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return builder().clientConnector(createClientHttpConnector()).build();
    }

    private ClientHttpConnector createClientHttpConnector() {
        final var httpClient = create().wiretap(HttpClient.class.getName(), DEBUG, TEXTUAL);
        return new ReactorClientHttpConnector(httpClient);
    }
}
