package com.stevesoltys.telegramirc.configuration.telegram;

import com.stevesoltys.telegramirc.configuration.ConfigurationException;
import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.image.impl.DefaultImageDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Steve Soltys
 */
@Component
public class TelegramDecoderConfiguration {

    private static final String CONFIGURATION_KEY = "decoders";

    private static final String IMAGE_DECODER_KEY = "image_decoder";

    private static final String STICKER_DECODER_KEY = "sticker_decoder";

    private static final String DEFAULT_IMAGE_DECODER = DefaultImageDecoder.IDENTIFIER;

    private static final String DEFAULT_STICKER_DECODER = DefaultImageDecoder.IDENTIFIER;

    private final ImgurDecoderConfiguration imgurDecoderConfiguration;

    private String imageDecoder;

    private String stickerDecoder;

    @Autowired
    public TelegramDecoderConfiguration(ImgurDecoderConfiguration imgurDecoderConfiguration) {
        this.imgurDecoderConfiguration = imgurDecoderConfiguration;

        imageDecoder = DEFAULT_IMAGE_DECODER;
    }

    @SuppressWarnings("unchecked")
    public void initialize(Map<String, Object> configuration) {
        Map<String, Object> decoderConfiguration = (Map<String, Object>) configuration.get(CONFIGURATION_KEY);

        if (decoderConfiguration == null) {
            throw new ConfigurationException("Could not find 'decoders' entry in 'telegram'.");
        }

        imageDecoder = (String) decoderConfiguration.getOrDefault(IMAGE_DECODER_KEY, DEFAULT_IMAGE_DECODER);
        stickerDecoder = (String) decoderConfiguration.getOrDefault(STICKER_DECODER_KEY, DEFAULT_STICKER_DECODER);

        imgurDecoderConfiguration.initialize(decoderConfiguration);
    }

    public String getImageDecoder() {
        return imageDecoder;
    }

    public String getStickerDecoder() {
        return stickerDecoder;
    }
}
