package com.stevesoltys.telegramirc.telegram.event;

import com.stevesoltys.telegramirc.telegram.bot.TelegramBot;
import org.telegram.telegrambots.api.objects.Message;

/**
 * @author Steve Soltys
 */
public class TelegramPrivateMessageEvent extends TelegramMessageEvent {

    public TelegramPrivateMessageEvent(TelegramBot telegramBot, Message message) {
        super(telegramBot, message);
    }
}
