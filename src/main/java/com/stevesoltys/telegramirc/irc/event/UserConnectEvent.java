package com.stevesoltys.telegramirc.irc.event;

import org.pircbotx.hooks.events.ConnectEvent;

/**
 * @author Steve Soltys
 */
public class UserConnectEvent extends IRCEvent<ConnectEvent> {

    public UserConnectEvent(ConnectEvent event) {
        super(event);
    }
}
