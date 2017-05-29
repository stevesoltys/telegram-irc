package com.stevesoltys.telegramirc.irc;

import com.stevesoltys.telegramirc.irc.event.UserConnectEvent;
import com.stevesoltys.telegramirc.irc.event.UserJoinEvent;
import com.stevesoltys.telegramirc.irc.event.UserMessageEvent;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectAttemptFailedEvent;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author Steve Soltys
 */
@Component
public class UserBot extends ListenerAdapter {

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserBot(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onJoin(JoinEvent event) throws Exception {
        eventPublisher.publishEvent(new UserJoinEvent(event));
    }

    @Override
    public void onConnect(ConnectEvent event) throws Exception {
        eventPublisher.publishEvent(new UserConnectEvent(event));
    }

    @Override
    public void onConnectAttemptFailed(ConnectAttemptFailedEvent event) throws Exception {
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        eventPublisher.publishEvent(new UserMessageEvent(event));
    }

}
