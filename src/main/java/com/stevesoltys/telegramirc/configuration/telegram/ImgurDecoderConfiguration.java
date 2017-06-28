package com.stevesoltys.telegramirc.configuration.telegram;

import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.image.impl.imgur.ImgurImageDecoder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Steve Soltys
 */
@Component
public class ImgurDecoderConfiguration {

    private static final String CONFIGURATION_KEY = ImgurImageDecoder.IDENTIFIER;

    private static final String DEFAULT_API_KEY = "ea6c0ef2987808e";

    private static final String API_KEY_CONFIGURATION_KEY = "api_key";

    private String apiKey;

    public ImgurDecoderConfiguration() {
        this.apiKey = DEFAULT_API_KEY;
    }

    @SuppressWarnings("unchecked")
    public void initialize(Map<String, Object> configuration) {
        Map<String, Object> decoderConfiguration = (Map<String, Object>) configuration.get(CONFIGURATION_KEY);

        if (decoderConfiguration == null) {
            return;
        }

        apiKey = (String) decoderConfiguration.getOrDefault(API_KEY_CONFIGURATION_KEY, DEFAULT_API_KEY);
    }

    public String getApiKey() {
        return apiKey;
    }
}
