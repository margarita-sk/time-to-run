package org.example.client.weatherclient;

import org.example.client.weatherclient.util.WeatherConverter;
import org.example.service.bo.DailyWeatherForecast;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class WeatherClientImpl implements WeatherClient {

    private final WebClient webClient;
    private final String url;
    private final String key;

    public WeatherClientImpl(WebClient webClient, @Value("${weather.url:}") String url,
                             @Value("${weather.access.key:}") String key) {
        this.webClient = webClient;
        this.url = url;
        this.key = key;
    }

    @Override
    public Mono<List<DailyWeatherForecast>> receiveForecast(String city, int forecastDays) {
        return webClient
                .get()
                .uri(url, uriBuilder -> uriBuilder
                        .queryParam("key", key)
                        .queryParam("q", city)
                        .queryParam("days", forecastDays)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(WeatherConverter::toDailyWeatherForecasts);
    }
}
