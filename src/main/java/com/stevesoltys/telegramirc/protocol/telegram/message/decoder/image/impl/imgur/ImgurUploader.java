package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.image.impl.imgur;

import com.stevesoltys.telegramirc.configuration.telegram.ImgurDecoderConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

/**
 * @author Steve Soltys
 */
@Component
public class ImgurUploader {

    private static final String UPLOAD_ENDPOINT = "https://api.imgur.com/3/image";

    private static final String IMAGE_PARAMETER_KEY = "image";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ImgurDecoderConfiguration configuration;

    private final RestTemplate restTemplate;

    public ImgurUploader(ImgurDecoderConfiguration configuration) {
        this.configuration = configuration;

        restTemplate = new RestTemplate();
    }

    public Optional<String> upload(String imageUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Client-ID " + configuration.getApiKey());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(IMAGE_PARAMETER_KEY, imageUrl);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<ImgurResponse> response;

        try {
            response = restTemplate.exchange(UPLOAD_ENDPOINT, HttpMethod.POST, entity, ImgurResponse.class);

        } catch (HttpClientErrorException ex) {
            logger.error("Error while uploading image to Imgur.", ex);
            return Optional.empty();
        }

        ImgurResponse imgurResponse = response.getBody();
        ImgurResponseData responseData = imgurResponse.getResponseData();

        if (responseData == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(responseData.getLink());
    }
}
