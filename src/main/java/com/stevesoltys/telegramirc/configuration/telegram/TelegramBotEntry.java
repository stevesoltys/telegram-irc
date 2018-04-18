package com.stevesoltys.telegramirc.configuration.telegram;

/**
 * @author Steve Soltys
 */
public class TelegramBotEntry {

    private String nick;

    private String username;

    private String token;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}