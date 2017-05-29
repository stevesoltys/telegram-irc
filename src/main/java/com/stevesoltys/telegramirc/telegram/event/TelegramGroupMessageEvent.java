package com.stevesoltys.telegramirc.telegram.event;

import com.stevesoltys.telegramirc.telegram.bot.TelegramBot;
import org.telegram.telegrambots.api.objects.Message;

/**
 * @author Steve Soltys
 */
public class TelegramGroupMessageEvent extends TelegramMessageEvent {

    public TelegramGroupMessageEvent(TelegramBot telegramBot, Message message) {
        super(telegramBot, message);
    }
}
