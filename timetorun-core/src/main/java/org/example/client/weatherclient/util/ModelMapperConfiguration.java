package org.example.client.weatherclient.util;

import org.example.client.weatherclient.dto.DailyWeatherForecastDTO;
import org.example.client.weatherclient.dto.WeatherDTO;
import org.example.service.bo.DailyWeatherForecast;
import org.example.service.bo.Weather;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Optional.ofNullable;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return configureModelMapper(modelMapper);
    }

    private ModelMapper configureModelMapper(ModelMapper modelMapper) {
        modelMapper.typeMap(WeatherDTO.class, Weather.class);

        modelMapper.typeMap(DailyWeatherForecastDTO.class, DailyWeatherForecast.class)
                .setPostConverter(this::convertDateTimeToDate);

        return modelMapper;
    }

    private DailyWeatherForecast convertDateTimeToDate(
            MappingContext<DailyWeatherForecastDTO, DailyWeatherForecast> context) {
        final var forecastDTO = context.getSource();
        final var forecast = context.getDestination();
        ofNullable(forecastDTO.getAstronomicData()).map(DailyWeatherForecastDTO.AstronomicData::getSunrise)
                .ifPresent(forecast::setSunriseTime);
        ofNullable(forecastDTO.getAstronomicData()).map(DailyWeatherForecastDTO.AstronomicData::getSunset)
                .ifPresent(forecast::setSunsetTime);
        ofNullable(forecastDTO.getAstronomicData()).map(DailyWeatherForecastDTO.AstronomicData::getMoonPhase)
                .ifPresent(forecast::setMoonPhase);
        return forecast;
    }

}
