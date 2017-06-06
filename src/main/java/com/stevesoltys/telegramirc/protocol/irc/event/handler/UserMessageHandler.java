package com.stevesoltys.telegramirc.protocol.irc.event.handler;

import com.stevesoltys.telegramirc.protocol.irc.IRCMessageEncoder;
import com.stevesoltys.telegramirc.protocol.irc.event.ActionMessageEvent;
import com.stevesoltys.telegramirc.protocol.irc.event.UserMessageEvent;
import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBot;
import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBotRepository;
import com.stevesoltys.telegramirc.protocol.telegram.user.TelegramUser;
import com.stevesoltys.telegramirc.protocol.telegram.user.TelegramUserRepository;
import org.pircbotx.hooks.types.GenericMessageEvent;
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

    private final IRCMessageEncoder messageEncoder;

    @Autowired
    public UserMessageHandler(TelegramBotRepository telegramBotRepository, TelegramUserRepository userRepository,
                              IRCMessageEncoder messageEncoder) {

        this.telegramBotRepository = telegramBotRepository;
        this.userRepository = userRepository;
        this.messageEncoder = messageEncoder;
    }

    @EventListener
    public void handlePrivateUserMessage(UserMessageEvent userMessageEvent) {
        handlePrivateMessage(userMessageEvent.getEvent(), false);
    }

    @EventListener
    public void handlePrivateActionMessage(ActionMessageEvent actionMessageEvent) {

        if (actionMessageEvent.getEvent().getChannel() != null) {
            return;
        }

        handlePrivateMessage(actionMessageEvent.getEvent(), true);
    }

    private void handlePrivateMessage(GenericMessageEvent event, boolean action) {

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
        String telegramId = telegramUser.getTelegramIdentifier();
        String message = event.getMessage();

        SendMessage telegramMessage = action ? messageEncoder.encodeActionMessage(telegramId, message) :
                messageEncoder.encodeMessage(telegramId, message);

        try {
            TelegramBot telegramBot = telegramBotOptional.get();
            telegramBot.sendMessage(telegramMessage);

        } catch (TelegramApiException e) {
            logger.error("Error while sending Telegram message: {}", e.toString());
        }
    }
}
