package org.example.service.bo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DailyWeatherForecast {

    private LocalDate date;
    private String sunriseTime;
    private String sunsetTime;
    private String moonPhase;
    private List<Weather> hourlyWeatherForecasts;

    @Override
    public String toString() {
        return String.format("\n%s %s \n(sunrise %s, sunset %s)", date.getDayOfWeek().name(), date, sunriseTime, sunriseTime) +
                String.format("\n\nBest time for running: \n%s",
                        hourlyWeatherForecasts.stream().map(Weather::toString).collect(Collectors.joining("\n")));
    }
}
