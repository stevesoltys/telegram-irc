package com.stevesoltys.telegramirc.protocol.irc.event.handler;

import com.stevesoltys.telegramirc.protocol.irc.event.OperatorJoinEvent;
import com.stevesoltys.telegramirc.protocol.irc.event.UserJoinEvent;
import com.stevesoltys.telegramirc.protocol.telegram.user.TelegramUser;
import com.stevesoltys.telegramirc.protocol.telegram.user.TelegramUserRepository;
import org.pircbotx.PircBotX;
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
public class JoinHandler {

    private static final String OPERATOR_MODE = "+o";

    private final TelegramUserRepository userRepository;

    @Autowired
    public JoinHandler(TelegramUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    public void handleUserJoin(UserJoinEvent userJoinEvent) {
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

    @EventListener
    public void handleOperatorJoin(OperatorJoinEvent operatorJoinEvent) {
        JoinEvent event = operatorJoinEvent.getEvent();
        String channel = event.getChannel().getName();

        PircBotX bot = event.getBot();
        bot.sendIRC().mode(channel, OPERATOR_MODE + " " + bot.getNick());
    }
}
