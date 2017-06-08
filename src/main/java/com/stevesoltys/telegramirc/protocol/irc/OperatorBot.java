package com.stevesoltys.telegramirc.protocol.irc;

import com.stevesoltys.telegramirc.protocol.irc.event.*;
import com.stevesoltys.telegramirc.protocol.telegram.TelegramProtocol;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author Steve Soltys
 */
@Component
public class OperatorBot extends ListenerAdapter {

    public static final String DEFAULT_NICK = "TelegramIRC";

    private final TelegramProtocol telegramProtocol;

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public OperatorBot(TelegramProtocol telegramProtocol, ApplicationEventPublisher eventPublisher) {
        this.telegramProtocol = telegramProtocol;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onConnect(ConnectEvent event) throws Exception {
        eventPublisher.publishEvent(new OperatorConnectEvent(event));
        telegramProtocol.initialize();
    }

    @Override
    public void onJoin(JoinEvent event) throws Exception {
        eventPublisher.publishEvent(new OperatorJoinEvent(event));
    }

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        eventPublisher.publishEvent(new ChannelMessageEvent(event));
    }

    @Override
    public void onAction(ActionEvent event) throws Exception {
        if (event.getChannel() != null) {
            eventPublisher.publishEvent(new ActionMessageEvent(event));
        }
    }
}
