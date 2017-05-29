package com.stevesoltys.telegramirc.irc.event.handler;

import com.stevesoltys.telegramirc.irc.event.UserJoinEvent;
import com.stevesoltys.telegramirc.telegram.user.TelegramUser;
import com.stevesoltys.telegramirc.telegram.user.TelegramUserRepository;
import org.pircbotx.hooks.events.JoinEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author Steve Soltys
 */
@Component
public class UserJoinHandler {

    private final TelegramUserRepository userRepository;

    @Autowired
    public UserJoinHandler(TelegramUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    public void handle(UserJoinEvent userJoinEvent) {
        JoinEvent event = userJoinEvent.getEvent();

        String receiverNick = event.getBot().getNick();
        Optional<TelegramUser> telegramUserOptional = userRepository.findByIrcIdentifier(receiverNick);

        if (!telegramUserOptional.isPresent()) {
            return;
        }

        TelegramUser telegramUser = telegramUserOptional.get();

        if (!telegramUser.getIrcBot().getUserBot().equals(event.getUser())) {
            return;
        }

        String channel = event.getChannel().getName();
        List<String> pendingChannelMessages = telegramUser.removePendingChannelMessages(channel);
        pendingChannelMessages.forEach(message -> telegramUser.getIrcBot().sendIRC().message(channel, message));
    }
}
