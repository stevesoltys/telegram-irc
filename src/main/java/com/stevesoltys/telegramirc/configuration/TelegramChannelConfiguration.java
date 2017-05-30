package com.stevesoltys.telegramirc.configuration;

import com.stevesoltys.telegramirc.configuration.ConfigurationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Steve Soltys
 */
@Component
public class TelegramChannelConfiguration {

    private static final String CHANNELS_KEY = "channels";

    private final Map<String, String> channelEntries;

    public TelegramChannelConfiguration() {
        channelEntries = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public void initialize(Map<String, Object> configuration) {
        Map<String, Object> ircConfiguration = (Map<String, Object>) configuration.get(CHANNELS_KEY);

        if (ircConfiguration == null) {
            throw new ConfigurationException("Could not find 'channels' entry in 'telegram'.");
        }

        for(String channelIdentifier : ircConfiguration.keySet()) {
            channelEntries.put(channelIdentifier, ircConfiguration.getOrDefault(channelIdentifier, "").toString());
        }
    }

    public String getChannelIdentifier(String telegramId) {
        return channelEntries.getOrDefault(telegramId, telegramId);
    }
}
