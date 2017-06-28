package com.stevesoltys.telegramirc.configuration.telegram;

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

    private static final String CONFIGURATION_KEY = "telegram";

    private final TelegramChannelConfiguration channelConfiguration;

    private final TelegramBotConfiguration botConfiguration;

    private final TelegramDecoderConfiguration decoderConfiguration;

    @Autowired
    public TelegramConfiguration(ConfigurationEntryRepository configurationEntryRepository,
                                 TelegramChannelConfiguration channelConfiguration,
                                 TelegramBotConfiguration botConfiguration,
                                 TelegramDecoderConfiguration decoderConfiguration) {
        super(configurationEntryRepository);

        this.channelConfiguration = channelConfiguration;
        this.botConfiguration = botConfiguration;
        this.decoderConfiguration = decoderConfiguration;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(Map<String, Object> configuration) {
        Map<String, Object> ircConfiguration = (Map<String, Object>) configuration.get(CONFIGURATION_KEY);

        if (ircConfiguration == null) {
            throw new ConfigurationException("Could not find 'telegram' entry.");
        }

        channelConfiguration.initialize(ircConfiguration);
        botConfiguration.initialize(ircConfiguration);
        decoderConfiguration.initialize(ircConfiguration);
    }
}
