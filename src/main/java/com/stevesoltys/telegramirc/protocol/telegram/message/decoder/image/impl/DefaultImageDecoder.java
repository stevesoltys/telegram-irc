package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.image.impl;

import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.image.ImageDecoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author Steve Soltys
 */
@Component
public class DefaultImageDecoder extends ImageDecoder {

    public static final String IDENTIFIER = "default";

    @Override
    public List<String> decode(String imageUrl) {
        return Collections.singletonList("(Photo)");
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }
}
