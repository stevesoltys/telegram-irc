package com.stevesoltys.telegramirc.protocol.telegram.message.decoder;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * @author Steve Soltys
 */
public abstract class FileDecoder {

    @Autowired
    private FileDecoderRepository fileDecoderRepository;

    @PostConstruct
    public void initialize() {
        fileDecoderRepository.register(this);
    }

    public abstract Optional<String> decode(String fileUrl);

    public abstract long maxFileSize();

    public abstract String getIdentifier();
}
