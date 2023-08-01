package org.example.client.weatherclient;

import org.example.service.bo.DailyWeatherForecast;
import reactor.core.publisher.Mono;

import java.util.List;

public interface WeatherClient {
    Mono<List<DailyWeatherForecast>> receiveForecast(String city, int forecastDays);
}
