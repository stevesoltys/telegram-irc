package com.stevesoltys.telegramirc.protocol.irc.event;

import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * @author Steve Soltys
 */
public class UserMessageEvent extends IRCEvent<PrivateMessageEvent> {

    public UserMessageEvent(PrivateMessageEvent event) {
        super(event);
    }
}
