package com.stevesoltys.telegramirc.protocol.irc.event;

import org.pircbotx.hooks.events.JoinEvent;

/**
 * @author Steve Soltys
 */
public class OperatorJoinEvent extends IRCEvent<JoinEvent> {

    public OperatorJoinEvent(JoinEvent event) {
        super(event);
    }
}
