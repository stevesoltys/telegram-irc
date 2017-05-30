package com.stevesoltys.telegramirc.protocol.telegram.bot;

import com.stevesoltys.telegramirc.protocol.telegram.event.TelegramGroupMessageEvent;
import com.stevesoltys.telegramirc.protocol.telegram.event.TelegramMessageEvent;
import com.stevesoltys.telegramirc.protocol.telegram.event.TelegramPrivateMessageEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.Optional;

/**
 * @author Steve Soltys
 */
public class TelegramBot extends TelegramLongPollingBot {

    private final ApplicationEventPublisher eventPublisher;

    private final String username;

    private final String token;

    public TelegramBot(ApplicationEventPublisher eventPublisher, String username, String token) {
        this.eventPublisher = eventPublisher;
        this.username = username;
        this.token = token;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (!update.hasMessage()) {
            return;
        }

        Message message = update.getMessage();
        Chat chat = message.getChat();

        Optional<TelegramMessageEvent> eventOptional = Optional.empty();

        if (chat.isGroupChat() || chat.isSuperGroupChat()) {
            eventOptional = Optional.of(new TelegramGroupMessageEvent(this, message));

        } else if (chat.isUserChat()) {
            eventOptional = Optional.of(new TelegramPrivateMessageEvent(this, message));
        }

        eventOptional.ifPresent(eventPublisher::publishEvent);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

}
