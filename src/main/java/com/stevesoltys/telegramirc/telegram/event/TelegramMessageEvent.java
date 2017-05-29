package com.stevesoltys.telegramirc.telegram.event;

import com.stevesoltys.telegramirc.telegram.bot.TelegramBot;
import org.telegram.telegrambots.api.objects.Message;

/**
 * @author Steve Soltys
 */
public abstract class TelegramMessageEvent {

    private final TelegramBot telegramBot;

    private final Message message;

    public TelegramMessageEvent(TelegramBot telegramBot, Message message) {
        this.telegramBot = telegramBot;
        this.message = message;
    }

    public TelegramBot getTelegramBot() {
        return telegramBot;
    }

    public Message getMessage() {
        return message;
    }

}
