package com.stevesoltys.telegramirc.protocol.telegram.message;

import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBot;
import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.image.TelegramImageMessageDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Document;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Sticker;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Steve Soltys
 */
@Component
public class TelegramMessageDecoder {

    private static final int MAX_IRC_MESSAGE_LENGTH = 480;

    private final TelegramImageMessageDecoder telegramImageMessageDecoder;

    @Autowired
    public TelegramMessageDecoder(TelegramImageMessageDecoder telegramImageMessageDecoder) {
        this.telegramImageMessageDecoder = telegramImageMessageDecoder;
    }

    public List<String> decode(TelegramBot telegramBot, Message telegramMessage) {
        return decode(telegramBot, telegramMessage, false);
    }

    private List<String> decode(TelegramBot telegramBot, Message telegramMessage, boolean quotedMessage) {
        List<String> messages = new LinkedList<>();

        if (telegramMessage.isReply() && !quotedMessage) {
            Message replyToMessage = telegramMessage.getReplyToMessage();

            if (replyToMessage.getFrom() != null) {
                String quotedMessageUsername = replyToMessage.getFrom().getUserName();

                decode(telegramBot, replyToMessage, true).forEach(text -> {
                    String quotedText = text.substring(0,
                            text.length() > MAX_IRC_MESSAGE_LENGTH ? MAX_IRC_MESSAGE_LENGTH : text.length());

                    messages.add("<" + quotedMessageUsername + "> " + quotedText);
                });
            }
        }

        if (telegramMessage.hasDocument()) {
            Document document = telegramMessage.getDocument();

            messages.add("(" + document.getMimeType() + ")");
        }

        if (telegramMessage.getSticker() != null) {
            Sticker sticker = telegramMessage.getSticker();

            messages.add("(Sticker " + sticker.getEmoji() + ")");
        }

        if (telegramMessage.getPhoto() != null) {
            messages.addAll(telegramImageMessageDecoder.decode(telegramBot, telegramMessage));
        }

        if (telegramMessage.getCaption() != null) {
            messages.addAll(decodeMultiLineMessages(telegramMessage.getCaption()));
        }

        if (telegramMessage.hasText()) {
            messages.addAll(decodeMultiLineMessages(telegramMessage.getText()));
        }

        return messages;
    }

    private List<String> decodeMultiLineMessages(String text) {
        return Arrays.asList(text.split("\r\n?|\n"));
    }

}
