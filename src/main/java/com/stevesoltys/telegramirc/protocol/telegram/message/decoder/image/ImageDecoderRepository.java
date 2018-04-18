package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.image;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Steve Soltys
 */
@Repository
public class ImageDecoderRepository {

    private final Map<String, ImageDecoder> repository;

    public ImageDecoderRepository() {
        repository = new HashMap<>();
    }

    public Optional<ImageDecoder> get(String identifier) {
        return Optional.ofNullable(repository.get(identifier));
    }

    public boolean register(ImageDecoder imageDecoder) {
        String decoderIdentifier = imageDecoder.getIdentifier();

        if (repository.containsKey(decoderIdentifier)) {
            return false;
        }

        repository.put(decoderIdentifier, imageDecoder);
        return true;
    }
}
