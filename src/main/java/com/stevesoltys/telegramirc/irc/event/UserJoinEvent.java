package com.stevesoltys.telegramirc.irc.event;

import org.pircbotx.hooks.events.JoinEvent;

/**
 * @author Steve Soltys
 */
public class UserJoinEvent extends IRCEvent<JoinEvent> {

    public UserJoinEvent(JoinEvent event) {
        super(event);
    }
}
