package com.stevesoltys.telegramirc.telegram.configuration;

import com.stevesoltys.telegramirc.configuration.ConfigurationEntry;
import com.stevesoltys.telegramirc.configuration.ConfigurationEntryRepository;
import com.stevesoltys.telegramirc.configuration.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Steve Soltys
 */
@Component
public class TelegramConfiguration extends ConfigurationEntry {

    private static final String TELEGRAM_KEY = "telegram";

    private final TelegramChannelConfiguration channelConfiguration;

    private final TelegramBotConfiguration botConfiguration;

    @Autowired
    public TelegramConfiguration(ConfigurationEntryRepository configurationEntryRepository,
                                 TelegramChannelConfiguration channelConfiguration,
                                 TelegramBotConfiguration botConfiguration) {
        super(configurationEntryRepository);

        this.channelConfiguration = channelConfiguration;
        this.botConfiguration = botConfiguration;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(Map<String, Object> configuration) {
        Map<String, Object> ircConfiguration = (Map<String, Object>) configuration.get(TELEGRAM_KEY);

        if (ircConfiguration == null) {
            throw new ConfigurationException("Could not find 'telegram' entry.");
        }

        channelConfiguration.initialize(ircConfiguration);
        botConfiguration.initialize(ircConfiguration);
    }
}
