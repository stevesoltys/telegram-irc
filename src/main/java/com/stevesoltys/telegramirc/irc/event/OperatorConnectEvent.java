package com.stevesoltys.telegramirc.irc.event;

import org.pircbotx.hooks.events.ConnectEvent;

/**
 * @author Steve Soltys
 */
public class OperatorConnectEvent extends IRCEvent<ConnectEvent> {

    public OperatorConnectEvent(ConnectEvent event) {
        super(event);
    }
}
