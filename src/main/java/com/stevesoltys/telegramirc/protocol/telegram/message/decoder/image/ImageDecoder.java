package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Steve Soltys
 */
@Component
public abstract class ImageDecoder {

    @Autowired
    private ImageDecoderRepository imageDecoderRepository;

    @PostConstruct
    public void initialize() {
        imageDecoderRepository.register(this);
    }

    public abstract List<String> decode(String imageUrl);

    public abstract String getIdentifier();
}
