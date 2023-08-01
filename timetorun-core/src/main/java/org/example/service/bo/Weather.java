package org.example.service.bo;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Weather {

    private LocalTime time;

    private Double temperature;

    private String condition;

    private Double windKph;

    private Integer humidity;

    private Double uv;

    @Override
    public String toString() {
        return String.format("\uD83C\uDFC3 %s (%s, %s)", time, temperature, condition);
    }
}
