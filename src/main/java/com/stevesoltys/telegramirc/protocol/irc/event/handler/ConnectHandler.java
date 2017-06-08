package com.stevesoltys.telegramirc.protocol.irc.event.handler;

import com.stevesoltys.telegramirc.configuration.IRCConfiguration;
import com.stevesoltys.telegramirc.protocol.irc.event.OperatorConnectEvent;
import com.stevesoltys.telegramirc.protocol.irc.event.UserConnectEvent;
import com.stevesoltys.telegramirc.protocol.telegram.user.TelegramUser;
import com.stevesoltys.telegramirc.protocol.telegram.user.TelegramUserRepository;
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
public class ConnectHandler {

    private static final String RAW_OPERATOR_COMMAND = "OPER";

    private final TelegramUserRepository userRepository;

    private final IRCConfiguration ircConfiguration;

    @Autowired
    public ConnectHandler(TelegramUserRepository userRepository, IRCConfiguration ircConfiguration) {
        this.userRepository = userRepository;
        this.ircConfiguration = ircConfiguration;
    }

    @EventListener
    public void handleUserConnection(UserConnectEvent userConnectEvent) {
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

    @EventListener
    public void handleOperatorConnection(OperatorConnectEvent userConnectEvent) {
        ConnectEvent event = userConnectEvent.getEvent();

        String operatorPassword = ircConfiguration.getOperatorPassword();
        if (!operatorPassword.isEmpty()) {
            PircBotX bot = event.getBot();

            bot.sendRaw().rawLine(RAW_OPERATOR_COMMAND + " " + bot.getNick() + " " + operatorPassword);
        }
    }
}
