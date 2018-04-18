package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.image.impl.imgur;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Steve Soltys
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImgurResponseData {

    @JsonProperty("link")
    private final String link;

    ImgurResponseData(String link) {
        this.link = link;
    }

    ImgurResponseData() {
        this(null);
    }

    String getLink() {
        return link;
    }
}
