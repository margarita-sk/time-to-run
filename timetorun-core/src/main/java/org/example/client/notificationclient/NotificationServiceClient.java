package org.example.client.notificationclient;

import org.example.client.notificationclient.dto.WeeklyTimeToRunNotification;
import reactor.core.publisher.Mono;

public interface NotificationServiceClient {
    void sendEveryone(Mono<WeeklyTimeToRunNotification> timeToRunNotification);

    void sendToChat(long chatId, Mono<WeeklyTimeToRunNotification> timeToRunNotification);
}
