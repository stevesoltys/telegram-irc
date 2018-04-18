package com.stevesoltys.telegramirc.configuration.telegram;

import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.mixtape.MixtapeFileDecoder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Steve Soltys
 */
@Configuration
@ConfigurationProperties("telegram")
public class TelegramDecoderConfiguration {

    private String imageDecoder = MixtapeFileDecoder.IDENTIFIER;

    private String stickerDecoder = MixtapeFileDecoder.IDENTIFIER;

    private String documentDecoder = MixtapeFileDecoder.IDENTIFIER;

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

    public String getDocumentDecoder() {
        return documentDecoder;
    }

    public void setDocumentDecoder(String documentDecoder) {
        this.documentDecoder = documentDecoder;
    }
}
