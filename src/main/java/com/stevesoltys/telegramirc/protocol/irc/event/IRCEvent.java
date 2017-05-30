package com.stevesoltys.telegramirc.protocol.irc.event;

import org.pircbotx.hooks.Event;

/**
 * @author Steve Soltys
 */
public class IRCEvent<T extends Event> {

    private final T event;

    IRCEvent(T event) {
        this.event = event;
    }

    public T getEvent() {
        return event;
    }
}
