package com.stevesoltys.telegramirc.protocol.telegram.message;

import com.stevesoltys.telegramirc.configuration.telegram.TelegramDecoderConfiguration;
import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBot;
import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.FileDecoder;
import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.FileDecoderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Steve Soltys
 */
@Component
public class TelegramDocumentMessageDecoder extends TelegramFileMessageDecoder {

    private final TelegramDecoderConfiguration telegramDecoderConfiguration;

    private final FileDecoderRepository fileDecoderRepository;

    @Autowired
    public TelegramDocumentMessageDecoder(TelegramDecoderConfiguration telegramDecoderConfiguration,
                                          FileDecoderRepository fileDecoderRepository) {
        this.telegramDecoderConfiguration = telegramDecoderConfiguration;
        this.fileDecoderRepository = fileDecoderRepository;
    }

    public List<String> decode(TelegramBot telegramBot, Message message) {
        Document document = message.getDocument();
        String decoderIdentifier = telegramDecoderConfiguration.getDocumentDecoder();
        Optional<FileDecoder> fileDecoderOptional = fileDecoderRepository.get(decoderIdentifier);

        if (fileDecoderOptional.isPresent()) {
            List<String> result = decode(telegramBot, fileDecoderOptional.get(), document.getFileId());

            if (!result.isEmpty()) {
                return result;
            }
        }

        return Collections.singletonList("(Document " + document.getMimeType() + ")");
    }
}
