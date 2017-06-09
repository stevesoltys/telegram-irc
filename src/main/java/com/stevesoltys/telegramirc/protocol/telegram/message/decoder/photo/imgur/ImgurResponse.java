package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.photo.imgur;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Steve Soltys
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImgurResponse {

    @JsonProperty("data")
    private final ImgurResponseData responseData;

    public ImgurResponse(ImgurResponseData responseData) {
        this.responseData = responseData;
    }

    private ImgurResponse() {
        this(null);
    }

    public ImgurResponseData getResponseData() {
        return responseData;
    }
}
