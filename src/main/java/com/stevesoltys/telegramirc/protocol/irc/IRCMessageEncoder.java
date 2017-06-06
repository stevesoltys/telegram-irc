package com.stevesoltys.telegramirc.protocol.irc;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;

/**
 * @author Steve Soltys
 */
@Component
public class IRCMessageEncoder {

    public SendMessage encodeActionMessage(String chatId, String action) {
        return new SendMessage().setChatId(chatId).setText("*" + action + "*");
    }

    public SendMessage encodeMessage(String chatId, String message) {
        return new SendMessage().setChatId(chatId).setText(message);
    }
}
