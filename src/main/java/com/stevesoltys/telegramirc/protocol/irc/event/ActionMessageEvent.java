package com.stevesoltys.telegramirc.protocol.irc.event;

import org.pircbotx.hooks.events.ActionEvent;

/**
 * @author Steve Soltys
 */
public class ActionMessageEvent extends IRCEvent<ActionEvent> {

    public ActionMessageEvent(ActionEvent event) {
        super(event);
    }
}
