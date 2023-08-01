package org.example.client.notificationclient;

import org.example.client.notificationclient.dto.WeeklyTimeToRunNotification;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TelegramBotClient implements NotificationServiceClient {

    private final TelegramBot telegramBot;
    private Set<Long> chatIds;

    public TelegramBotClient(@Value("${telegram.token}") String telegramToken) {
        this.telegramBot = new TelegramBot(telegramToken);
        chatIds = telegramBot.execute(new GetUpdates()).updates().stream().map(Update::message).map(Message::chat)
                .map(Chat::id).collect(Collectors.toSet());
    }

    @Override
    public void sendEveryone(Mono<WeeklyTimeToRunNotification> timeToRunNotification) {
        chatIds.forEach(chatId -> sendToChat(chatId, timeToRunNotification));
    }

    @Override
    public void sendToChat(long chatId, Mono<WeeklyTimeToRunNotification> timeToRunNotification) {
        timeToRunNotification.map(WeeklyTimeToRunNotification::getMessages)
                .map(messages -> messages.stream().map(message -> new SendMessage(chatId, message)).toList())
                .toFuture()
                .thenAcceptAsync(messages -> messages.forEach(telegramBot::execute));
    }
}
