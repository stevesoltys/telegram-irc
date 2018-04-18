package com.stevesoltys.telegramirc.configuration.telegram;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Steve Soltys
 */
@Configuration
@ConfigurationProperties("telegram.decoders.imgur")
public class ImgurDecoderConfiguration {

    private static final String DEFAULT_API_KEY = "ea6c0ef2987808e";

    private String apiKey = DEFAULT_API_KEY;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
