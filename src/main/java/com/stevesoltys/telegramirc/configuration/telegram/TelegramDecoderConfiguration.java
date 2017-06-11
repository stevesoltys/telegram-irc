package com.stevesoltys.telegramirc.configuration.telegram;

import com.stevesoltys.telegramirc.configuration.ConfigurationException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Steve Soltys
 */
@Component
public class TelegramDecoderConfiguration {

    private static final String DECODERS_KEY = "decoders";

    public TelegramDecoderConfiguration() {
    }

    @SuppressWarnings("unchecked")
    public void initialize(Map<String, Object> configuration) {
        Map<String, Object> decoderConfiguration = (Map<String, Object>) configuration.get(DECODERS_KEY);

        if (decoderConfiguration == null) {
            throw new ConfigurationException("Could not find 'channels' entry in 'telegram'.");
        }


    }
}
