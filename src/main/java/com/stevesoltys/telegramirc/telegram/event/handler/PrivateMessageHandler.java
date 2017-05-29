package com.stevesoltys.telegramirc.telegram.event.handler;

import com.stevesoltys.telegramirc.telegram.TelegramMessageDecoder;
import com.stevesoltys.telegramirc.telegram.bot.TelegramBot;
import com.stevesoltys.telegramirc.telegram.bot.TelegramBotRepository;
import com.stevesoltys.telegramirc.telegram.event.TelegramPrivateMessageEvent;
import com.stevesoltys.telegramirc.telegram.user.TelegramUser;
import com.stevesoltys.telegramirc.telegram.user.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Optional;

/**
 * @author Steve Soltys
 */
@Component
public class PrivateMessageHandler {

    private final TelegramUserRepository userRepository;

    private final TelegramMessageDecoder telegramMessageDecoder;

    private final TelegramBotRepository telegramBotRepository;

    @Autowired
    public PrivateMessageHandler(TelegramUserRepository userRepository, TelegramMessageDecoder telegramMessageDecoder,
                                 TelegramBotRepository telegramBotRepository) {
        this.userRepository = userRepository;
        this.telegramMessageDecoder = telegramMessageDecoder;
        this.telegramBotRepository = telegramBotRepository;
    }

    @EventListener
    public void handle(TelegramPrivateMessageEvent event) {
        TelegramBot telegramBot = event.getTelegramBot();
        Message telegramMessage = event.getMessage();

        if (telegramMessage.getFrom() == null) {
            return;
        }

        Optional<String> ircIdentifierOptional = telegramBotRepository.findIrcIdentifier(telegramBot);

        ircIdentifierOptional.ifPresent(recipientIrcIdentifier -> {
            String telegramIdentifier = String.valueOf(telegramMessage.getFrom().getId());
            String senderIrcIdentifier = telegramMessage.getFrom().getUserName();

            TelegramUser user = userRepository.findOrRegister(telegramIdentifier, senderIrcIdentifier);

            telegramMessageDecoder.decode(telegramMessage).forEach(message ->
                    user.sendPrivateMessage(recipientIrcIdentifier, message));
        });

    }
}
