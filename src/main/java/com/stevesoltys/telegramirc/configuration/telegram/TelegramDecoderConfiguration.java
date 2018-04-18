package com.stevesoltys.telegramirc.configuration.telegram;

import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.image.impl.DefaultImageDecoder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Steve Soltys
 */
@Configuration
@ConfigurationProperties("telegram")
public class TelegramDecoderConfiguration {

    private String imageDecoder = DefaultImageDecoder.IDENTIFIER;

    private String stickerDecoder = DefaultImageDecoder.IDENTIFIER;

    public String getImageDecoder() {
        return imageDecoder;
    }

    public void setImageDecoder(String imageDecoder) {
        this.imageDecoder = imageDecoder;
    }

    public String getStickerDecoder() {
        return stickerDecoder;
    }

    public void setStickerDecoder(String stickerDecoder) {
        this.stickerDecoder = stickerDecoder;
    }
}
