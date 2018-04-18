package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.image.impl.imgur;

import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.image.ImageDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Steve Soltys
 */
@Component
public class ImgurImageDecoder extends ImageDecoder {

    public static final String IDENTIFIER = "imgur";

    private final ImgurUploader imgurUploader;

    @Autowired
    public ImgurImageDecoder(ImgurUploader imgurUploader) {
        this.imgurUploader = imgurUploader;
    }

    @Override
    public List<String> decode(String imageUrl) {
        Optional<String> urlOptional = imgurUploader.upload(imageUrl);

        return urlOptional.map(Collections::singletonList).orElseGet(Collections::emptyList);
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }
}
