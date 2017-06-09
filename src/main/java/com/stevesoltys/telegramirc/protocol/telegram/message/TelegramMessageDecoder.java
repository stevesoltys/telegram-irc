package com.stevesoltys.telegramirc.protocol.telegram.message;

import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBot;
import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.photo.TelegramPhotoMessageDecoder;
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

    private final TelegramPhotoMessageDecoder photoMessageDecoder;

    @Autowired
    public TelegramMessageDecoder(TelegramPhotoMessageDecoder photoMessageDecoder) {
        this.photoMessageDecoder = photoMessageDecoder;
    }

    public List<String> decode(TelegramBot telegramBot, Message telegramMessage) {
        List<String> messages = new LinkedList<>();

        if (telegramMessage.isReply()) {
            Message replyToMessage = telegramMessage.getReplyToMessage();

            if (replyToMessage.getFrom() != null && replyToMessage.hasText()) {
                int replyToMessageLength = replyToMessage.getText().length();

                String quotedText = replyToMessage.getText()
                        .substring(0, replyToMessageLength > 100 ? 100 : replyToMessageLength);

                messages.add("<" + replyToMessage.getFrom().getUserName() + "> " + quotedText);
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
            messages.addAll(photoMessageDecoder.decodePhotoMessage(telegramBot, telegramMessage));
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
