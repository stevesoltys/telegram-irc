package com.stevesoltys.telegramirc.protocol.telegram.message.decoder;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Steve Soltys
 */
@Repository
public class FileDecoderRepository {

    private final Map<String, FileDecoder> repository;

    public FileDecoderRepository() {
        repository = new HashMap<>();
    }

    public Optional<FileDecoder> get(String identifier) {
        return Optional.ofNullable(repository.get(identifier));
    }

    public boolean register(FileDecoder fileDecoder) {
        String decoderIdentifier = fileDecoder.getIdentifier();

        if (repository.containsKey(decoderIdentifier)) {
            return false;
        }

        repository.put(decoderIdentifier, fileDecoder);
        return true;
    }
}
