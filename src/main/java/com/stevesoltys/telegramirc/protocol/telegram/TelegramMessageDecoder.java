package com.stevesoltys.telegramirc.protocol.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Document;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Sticker;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Steve Soltys
 */
@Component
public class TelegramMessageDecoder {

    public List<String> decode(Message telegramMessage) {
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
            messages.add("(Photo)");
        }

        if (telegramMessage.hasText()) {
            messages.add(telegramMessage.getText());
        }

        return messages;
    }

}
