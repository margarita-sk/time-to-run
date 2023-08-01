package org.example.client.weatherclient.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyWeatherForecastDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonProperty("astro")
    private AstronomicData astronomicData;

    @JsonProperty("hour")
    private List<WeatherDTO> hourlyWeatherForecasts;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AstronomicData {
        private String sunrise;
        private String sunset;
        @JsonProperty("moon_phase")
        private String moonPhase;
    }
}
