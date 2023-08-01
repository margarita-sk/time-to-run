package org.example.client.notificationclient.dto;

import org.example.service.bo.DailyWeatherForecast;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WeeklyTimeToRunNotification {
    private List<String> messages;

    public WeeklyTimeToRunNotification(List<DailyWeatherForecast> messages) {
        this.messages = new ArrayList<>();
        this.messages.add("Hi! Let's plan your running week!\n");
        this.messages.addAll(messages.stream().map(DailyWeatherForecast::toString).toList());
        this.messages.add("\uD83D\uDDD3 \uD83D\uDC4C️ Have a nice week!\n");
    }
}
