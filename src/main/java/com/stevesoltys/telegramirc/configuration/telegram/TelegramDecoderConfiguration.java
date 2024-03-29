package com.stevesoltys.telegramirc.configuration.telegram;

import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.uguu.x0.UguuFileDecoder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Steve Soltys
 */
@Configuration
@ConfigurationProperties("telegram")
public class TelegramDecoderConfiguration {

    private String imageDecoder = UguuFileDecoder.IDENTIFIER;

    private String stickerDecoder = UguuFileDecoder.IDENTIFIER;

    private String documentDecoder = UguuFileDecoder.IDENTIFIER;

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
