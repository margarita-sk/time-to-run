package org.example.client.weatherclient.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalTime;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalTime time;

    @JsonProperty("temp_c")
    private Double temperature;

    private String condition;

    @JsonProperty("wind_kph")
    private Double windKph;

    private Integer humidity;

    private Double uv;

    @JsonProperty("condition")
    public void setCondition(Map<String, String> condition) {
        this.condition = condition.get("text");
    }
}



