package com.stevesoltys.telegramirc.telegram.channel;

/**
 * @author Steve Soltys
 */
public class TelegramChannel {

    private final String ircIdentifier;

    private final String telegramIdentifier;

    public TelegramChannel(String ircIdentifier, String telegramIdentifier) {
        this.ircIdentifier = ircIdentifier;
        this.telegramIdentifier = telegramIdentifier;
    }

    public String getIrcIdentifier() {
        return ircIdentifier;
    }

    public String getTelegramIdentifier() {
        return telegramIdentifier;
    }
}
