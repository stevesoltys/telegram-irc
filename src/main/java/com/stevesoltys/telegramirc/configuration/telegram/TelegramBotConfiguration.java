package com.stevesoltys.telegramirc.configuration.telegram;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Steve Soltys
 */
@Configuration
@ConfigurationProperties("telegram")
public class TelegramBotConfiguration {

    private List<TelegramBotEntry> bots;

    public List<TelegramBotEntry> getBots() {
        return bots;
    }

    public void setBots(List<TelegramBotEntry> bots) {
        this.bots = bots;
    }
}
