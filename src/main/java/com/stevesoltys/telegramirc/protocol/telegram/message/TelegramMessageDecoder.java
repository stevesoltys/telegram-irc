package com.stevesoltys.telegramirc.protocol.telegram.message;

import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Steve Soltys
 */
@Component
public class TelegramMessageDecoder {

    private static final int MAX_IRC_MESSAGE_LENGTH = 480;

    private final TelegramImageMessageDecoder imageMessageDecoder;

    private final TelegramStickerMessageDecoder stickerMessageDecoder;

    private final TelegramDocumentMessageDecoder documentMessageDecoder;

    @Autowired
    public TelegramMessageDecoder(TelegramImageMessageDecoder imageMessageDecoder,
                                  TelegramStickerMessageDecoder stickerMessageDecoder,
                                  TelegramDocumentMessageDecoder documentMessageDecoder) {
        this.imageMessageDecoder = imageMessageDecoder;
        this.stickerMessageDecoder = stickerMessageDecoder;
        this.documentMessageDecoder = documentMessageDecoder;
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
            messages.addAll(documentMessageDecoder.decode(telegramBot, telegramMessage));
        }

        if (telegramMessage.getSticker() != null) {
            messages.addAll(stickerMessageDecoder.decode(telegramBot, telegramMessage));
        }

        if (telegramMessage.getPhoto() != null) {
            messages.addAll(imageMessageDecoder.decode(telegramBot, telegramMessage));
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
