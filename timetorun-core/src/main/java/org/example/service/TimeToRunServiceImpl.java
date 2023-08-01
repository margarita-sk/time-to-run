package org.example.service;

import org.example.client.notificationclient.NotificationServiceClient;
import org.example.client.notificationclient.dto.WeeklyTimeToRunNotification;
import org.example.client.weatherclient.WeatherClient;
import org.example.service.bo.DailyWeatherForecast;
import org.example.service.bo.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeToRunServiceImpl implements TimeToRunService {

    private final List<NotificationServiceClient> notificationServiceClients;
    private final WeatherClient weatherClient;

    @Value("${weather.city:Wroclaw}")
    private String city;
    @Value("${weather.forecast.days:7}")
    private int forecastDays;

    @Override
    @Scheduled(cron = "0 0 12 ? * MON")
    public void sendWeatherInfoNotification() {
        var dailyWeatherForecasts = weatherClient.receiveForecast(city, forecastDays);

        var notification = dailyWeatherForecasts.map(this::applyFilters).map(WeeklyTimeToRunNotification::new);

        notificationServiceClients.forEach(notificationServiceClient -> notificationServiceClient.sendEveryone(notification));
    }

    private List<DailyWeatherForecast> applyFilters(List<DailyWeatherForecast> dailyWeatherForecasts) {
        return dailyWeatherForecasts.stream().map(this::filterEachHour)
                .sorted(Comparator.comparing(DailyWeatherForecast::getDate))
                .toList();
    }

    private DailyWeatherForecast filterEachHour(DailyWeatherForecast dailyWeatherForecast) {
        var hourWeathers = dailyWeatherForecast.getHourlyWeatherForecasts()
                .stream()
                .filter(filterByWakeUpAndSleepTime().and(filterByMaxAndMinTemperature()).and(filterByBusinessPlan()))
                .sorted(Comparator.comparing(Weather::getTime))
                .collect(Collectors.toCollection(ArrayList::new));
        dailyWeatherForecast.setHourlyWeatherForecasts(hourWeathers);
        return dailyWeatherForecast;
    }

    private Predicate<Weather> filterByWakeUpAndSleepTime() {
        return weather -> weather.getTime().isAfter(LocalTime.of(6, 0))
                && weather.getTime().isBefore(LocalTime.of(22, 00));
    }

    private Predicate<Weather> filterByMaxAndMinTemperature() {
        return weather -> weather.getTemperature().compareTo(Double.parseDouble("21")) <= 0
                && weather.getTemperature().compareTo(Double.parseDouble("-15")) >= 0;
    }

    private Predicate<Weather> filterByBusinessPlan() {
        return weather -> weather.getTime().isAfter(LocalTime.of(18, 0))
                || weather.getTime().isBefore(LocalTime.of(8, 00));
    }


}
