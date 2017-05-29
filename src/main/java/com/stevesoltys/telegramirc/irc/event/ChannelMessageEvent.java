package com.stevesoltys.telegramirc.irc.event;

import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author Steve Soltys
 */
public class ChannelMessageEvent extends IRCEvent<MessageEvent> {

    public ChannelMessageEvent(MessageEvent event) {
        super(event);
    }
}
