package com.stevesoltys.telegramirc.configuration.telegram;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author Steve Soltys
 */
@Configuration
@ConfigurationProperties("telegram")
public class TelegramChannelConfiguration {

    private Map<String, String> channels;

    public String getChannelIdentifier(String telegramId) {
        return channels.getOrDefault(telegramId, telegramId);
    }

    public Map<String, String> getChannels() {
        return channels;
    }

    public void setChannels(Map<String, String> channels) {
        this.channels = channels;
    }
}
