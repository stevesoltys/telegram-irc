package com.stevesoltys.telegramirc.irc.event;

import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * @author Steve Soltys
 */
public class UserMessageEvent extends IRCEvent<PrivateMessageEvent> {

    public UserMessageEvent(PrivateMessageEvent event) {
        super(event);
    }
}
