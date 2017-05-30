package com.stevesoltys.telegramirc.protocol.irc.event;

import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author Steve Soltys
 */
public class ChannelMessageEvent extends IRCEvent<MessageEvent> {

    public ChannelMessageEvent(MessageEvent event) {
        super(event);
    }
}
