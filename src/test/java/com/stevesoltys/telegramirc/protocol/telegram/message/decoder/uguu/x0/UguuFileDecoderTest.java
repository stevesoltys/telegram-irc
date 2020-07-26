package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.uguu.x0;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Steve Soltys
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UguuFileDecoderTest {

    private static final String GOOGLE_LOGO_IMAGE =
            "https://upload.wikimedia.org/wikipedia/commons/9/98/Google_Logo_Old.PNG";

    @Autowired
    private UguuFileDecoder fileDecoder;

    @Test
    public void decode() {
        Optional<String> result = fileDecoder.decode(GOOGLE_LOGO_IMAGE);

        assertThat(result.isPresent()).describedAs("Decoded file exists.").isTrue();
    }
}