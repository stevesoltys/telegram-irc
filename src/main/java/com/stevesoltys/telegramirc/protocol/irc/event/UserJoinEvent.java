package com.stevesoltys.telegramirc.protocol.irc.event;

import org.pircbotx.hooks.events.JoinEvent;

/**
 * @author Steve Soltys
 */
public class UserJoinEvent extends IRCEvent<JoinEvent> {

    public UserJoinEvent(JoinEvent event) {
        super(event);
    }
}
