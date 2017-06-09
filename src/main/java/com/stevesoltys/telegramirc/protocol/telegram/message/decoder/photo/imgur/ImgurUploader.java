package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.photo.imgur;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

/**
 * @author Steve Soltys
 */
@Component
public class ImgurUploader {

    private static final String IMGUR_ANONYMOUS_UPLOAD_IDENTIFIER = "ea6c0ef2987808e";

    private static final String UPLOAD_ENDPOINT = "https://api.imgur.com/3/image?image={image}";

    private static final String IMAGE_URI_VARIABLE = "image";

    private final RestTemplate restTemplate;

    public ImgurUploader() {
        restTemplate = new RestTemplate();
    }

    public Optional<String> upload(String imageUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Client-ID " + IMGUR_ANONYMOUS_UPLOAD_IDENTIFIER);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ImgurResponse> response = restTemplate.exchange(UPLOAD_ENDPOINT, HttpMethod.POST, entity,
                ImgurResponse.class, Collections.singletonMap(IMAGE_URI_VARIABLE, imageUrl));

        ImgurResponse imgurResponse = response.getBody();
        ImgurResponseData responseData = imgurResponse.getResponseData();

        if(responseData == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(responseData.getLink());
    }
}
