package com.stevesoltys.telegramirc.protocol.irc;

import com.stevesoltys.telegramirc.protocol.irc.event.ActionMessageEvent;
import com.stevesoltys.telegramirc.protocol.irc.event.UserConnectEvent;
import com.stevesoltys.telegramirc.protocol.irc.event.UserJoinEvent;
import com.stevesoltys.telegramirc.protocol.irc.event.UserMessageEvent;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;
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

    @Override
    public void onAction(ActionEvent event) throws Exception {
        if(event.getChannel() == null) {
            eventPublisher.publishEvent(new ActionMessageEvent(event));
        }
    }
}
