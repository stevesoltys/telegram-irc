package com.stevesoltys.telegramirc.irc.event.handler;

import com.stevesoltys.telegramirc.irc.event.UserConnectEvent;
import com.stevesoltys.telegramirc.telegram.user.TelegramUser;
import com.stevesoltys.telegramirc.telegram.user.TelegramUserRepository;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.ConnectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Steve Soltys
 */
@Component
public class UserConnectHandler {

    private final TelegramUserRepository userRepository;

    @Autowired
    public UserConnectHandler(TelegramUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    public void handle(UserConnectEvent userConnectEvent) {
        ConnectEvent event = userConnectEvent.getEvent();

        String receiverNick = event.getBot().getNick();
        Optional<TelegramUser> telegramUserOptional = userRepository.findByIrcIdentifier(receiverNick);

        if (!telegramUserOptional.isPresent()) {
            return;
        }

        TelegramUser telegramUser = telegramUserOptional.get();
        PircBotX ircBot = telegramUser.getIrcBot();

        telegramUser.getPendingChannels().forEach(channel -> ircBot.sendIRC().joinChannel(channel));

        telegramUser.removePendingPrivateMessages().forEach((username, messages) ->
                messages.forEach(message -> ircBot.sendIRC().message(username, message))
        );
    }
}
