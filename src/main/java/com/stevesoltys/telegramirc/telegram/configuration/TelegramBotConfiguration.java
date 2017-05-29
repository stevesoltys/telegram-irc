package com.stevesoltys.telegramirc.telegram.configuration;

import com.stevesoltys.telegramirc.configuration.ConfigurationException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Steve Soltys
 */
@Component
public class TelegramBotConfiguration {

    private static final String BOTS_KEY = "bots";

    private static final String NICK_KEY = "nick";

    private static final String USERNAME_KEY = "username";

    private static final String TOKEN_KEY = "token";

    private final Set<Entry> botEntries;

    public TelegramBotConfiguration() {
        botEntries = new HashSet<>();
    }

    @SuppressWarnings("unchecked")
    public void initialize(Map<String, Object> configuration) {
        List<Map<String, Object>> botConfiguration = (List<Map<String, Object>>) configuration.get(BOTS_KEY);

        if (botConfiguration == null) {
            throw new ConfigurationException("Could not find 'bots' entry in 'telegram'.");
        }

        for (Map<String, Object> entry : botConfiguration) {
            String nick = (String) entry.get(NICK_KEY);
            String username = (String) entry.get(USERNAME_KEY);
            String token = (String) entry.get(TOKEN_KEY);

            if (nick == null) {
                throw new ConfigurationException("Could not find 'nick' entry for a Telegram bot entry.");

            } else if (username == null) {
                throw new ConfigurationException("Could not find 'username' entry for a Telegram bot entry.");

            } else if (token == null) {
                throw new ConfigurationException("Could not find 'token' entry for a Telegram bot entry.");
            }

            botEntries.add(new Entry(nick, username, token));
        }
    }

    public Set<Entry> getBotEntries() {
        return botEntries;
    }

    public class Entry {

        private final String nick;

        private final String username;

        private final String token;

        private Entry(String nick, String username, String token) {
            this.nick = nick;
            this.username = username;
            this.token = token;
        }

        public String getNick() {
            return nick;
        }

        public String getUsername() {
            return username;
        }

        public String getToken() {
            return token;
        }
    }
}
