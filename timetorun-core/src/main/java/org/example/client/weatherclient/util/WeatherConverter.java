package org.example.client.weatherclient.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.client.weatherclient.dto.DailyWeatherForecastDTO;
import org.example.service.bo.DailyWeatherForecast;
import org.example.service.config.ApplicationContextHolder;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

import java.util.List;

@UtilityClass
public class WeatherConverter {

    private ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();

    public List<DailyWeatherForecast> toDailyWeatherForecasts(String json) {
        try {
            var forecastDayNode = mapper.readTree(json).get("forecast").get("forecastday");
            List<DailyWeatherForecastDTO> dailyForecastDTOs = null;

            dailyForecastDTOs = mapper.readValue(forecastDayNode.toString(), new TypeReference<List<DailyWeatherForecastDTO>>() {
            });

            var modelMapper = ApplicationContextHolder.getApplicationContext().getBean(ModelMapper.class);

            return dailyForecastDTOs.stream()
                    .map(dailyForecastDTO -> modelMapper.map(dailyForecastDTO, DailyWeatherForecast.class))
                    .toList();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
