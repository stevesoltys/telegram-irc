package com.stevesoltys.telegramirc.protocol.telegram.event.handler;

import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBot;
import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBotRepository;
import com.stevesoltys.telegramirc.protocol.telegram.event.TelegramPrivateMessageEvent;
import com.stevesoltys.telegramirc.protocol.telegram.message.TelegramMessageDecoder;
import com.stevesoltys.telegramirc.protocol.telegram.message.TelegramMessageRepository;
import com.stevesoltys.telegramirc.protocol.telegram.user.TelegramUser;
import com.stevesoltys.telegramirc.protocol.telegram.user.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

/**
 * @author Steve Soltys
 */
@Component
public class PrivateMessageHandler {

    private final TelegramUserRepository userRepository;

    private final TelegramMessageDecoder telegramMessageDecoder;

    private final TelegramBotRepository telegramBotRepository;

    private final TelegramMessageRepository messageRepository;

    @Autowired
    public PrivateMessageHandler(TelegramUserRepository userRepository, TelegramMessageDecoder telegramMessageDecoder,
                                 TelegramBotRepository telegramBotRepository,
                                 TelegramMessageRepository messageRepository) {

        this.userRepository = userRepository;
        this.telegramMessageDecoder = telegramMessageDecoder;
        this.telegramBotRepository = telegramBotRepository;
        this.messageRepository = messageRepository;
    }

    @EventListener
    public void handle(TelegramPrivateMessageEvent event) {
        TelegramBot telegramBot = event.getTelegramBot();
        Message telegramMessage = event.getMessage();

        if (telegramMessage.getFrom() == null || messageRepository.hasProcessed(telegramMessage)) {
            return;
        }

        messageRepository.add(telegramMessage);
        Optional<String> ircIdentifierOptional = telegramBotRepository.findIrcIdentifier(telegramBot);

        ircIdentifierOptional.ifPresent(recipientIrcIdentifier -> {
            String telegramIdentifier = String.valueOf(telegramMessage.getFrom().getId());
            String senderIrcIdentifier = telegramMessage.getFrom().getUserName();

            TelegramUser user = userRepository.findOrRegister(telegramIdentifier, senderIrcIdentifier);

            telegramMessageDecoder.decode(event.getTelegramBot(), telegramMessage).forEach(message ->
                    user.sendPrivateMessage(recipientIrcIdentifier, message));
        });

    }
}
