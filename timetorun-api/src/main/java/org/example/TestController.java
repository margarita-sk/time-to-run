package org.example;

import lombok.RequiredArgsConstructor;
import org.example.service.TimeToRunService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TimeToRunService timeToRunService;

    @GetMapping("/trigger")
    public void triggerWeatherInfoNotification() {
        timeToRunService.sendWeatherInfoNotification();
    }


}
