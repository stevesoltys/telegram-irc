package com.stevesoltys.telegramirc.irc.event.handler;

import com.stevesoltys.telegramirc.irc.event.UserMessageEvent;
import com.stevesoltys.telegramirc.telegram.user.TelegramUser;
import com.stevesoltys.telegramirc.telegram.bot.TelegramBot;
import com.stevesoltys.telegramirc.telegram.bot.TelegramBotRepository;
import com.stevesoltys.telegramirc.telegram.user.TelegramUserRepository;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Optional;

/**
 * @author Steve Soltys
 */
@Component
public class UserMessageHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TelegramBotRepository telegramBotRepository;

    private final TelegramUserRepository userRepository;

    @Autowired
    public UserMessageHandler(TelegramBotRepository telegramBotRepository, TelegramUserRepository userRepository) {
        this.telegramBotRepository = telegramBotRepository;
        this.userRepository = userRepository;
    }

    @EventListener
    public void handle(UserMessageEvent userMessageEvent) {
        PrivateMessageEvent event = userMessageEvent.getEvent();

        if (event.getUser() == null) {
            return;
        }

        String senderNick = event.getUser().getNick();
        String receiverNick = event.getBot().getNick();

        Optional<TelegramBot> telegramBotOptional = telegramBotRepository.findBot(senderNick);
        Optional<TelegramUser> telegramUserOptional = userRepository.findByIrcIdentifier(receiverNick);

        if (!telegramUserOptional.isPresent() || !telegramBotOptional.isPresent()) {
            return;
        }

        TelegramUser telegramUser = telegramUserOptional.get();
        TelegramBot telegramBot = telegramBotOptional.get();

        SendMessage message = new SendMessage()
                .setText(event.getMessage())
                .setChatId(telegramUser.getTelegramIdentifier());

        try {
            telegramBot.sendMessage(message);

        } catch (TelegramApiException e) {
            logger.error("Error while sending Telegram message: {}", e.toString());
        }
    }
}
