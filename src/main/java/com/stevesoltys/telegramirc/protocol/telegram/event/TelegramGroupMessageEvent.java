package com.stevesoltys.telegramirc.protocol.telegram.event;

import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Steve Soltys
 */
public class TelegramGroupMessageEvent extends TelegramMessageEvent {

    public TelegramGroupMessageEvent(TelegramBot telegramBot, Message message) {
        super(telegramBot, message);
    }
}
