package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.image;

import com.stevesoltys.telegramirc.configuration.telegram.TelegramDecoderConfiguration;
import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Sticker;

import java.util.*;

/**
 * @author Steve Soltys
 */
@Component
public class TelegramStickerMessageDecoder {

    private static final String TELEGRAM_BOT_FILE_ENDPOINT = "https://api.telegram.org/file/bot";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TelegramDecoderConfiguration telegramDecoderConfiguration;

    private final ImageDecoderRepository imageDecoderRepository;

    @Autowired
    public TelegramStickerMessageDecoder(TelegramDecoderConfiguration telegramDecoderConfiguration,
                                         ImageDecoderRepository imageDecoderRepository) {
        this.telegramDecoderConfiguration = telegramDecoderConfiguration;
        this.imageDecoderRepository = imageDecoderRepository;
    }

    public List<String> decode(TelegramBot telegramBot, Message message) {
        Sticker sticker = message.getSticker();
        Optional<String> filePathOptional = getFilePath(telegramBot, sticker);

        if (filePathOptional.isPresent()) {
            String telegramBotToken = telegramBot.getBotToken();
            String filePath = filePathOptional.get();
            String url = TELEGRAM_BOT_FILE_ENDPOINT + telegramBotToken + "/" + filePath;

            String imageDecoderIdentifier = telegramDecoderConfiguration.getStickerDecoder();
            Optional<ImageDecoder> imageDecoderOptional = imageDecoderRepository.get(imageDecoderIdentifier);

            if (imageDecoderOptional.isPresent()) {
                List<String> decodedImageResults = imageDecoderOptional.get().decode(url);
                List<String> results = new LinkedList<>();

                for (String imageResult : decodedImageResults) {
                    results.add("(Sticker " + sticker.getEmoji() + " - " + imageResult + ")");
                }

                return results;
            }
        }

        return Collections.emptyList();
    }

    private Optional<String> getFilePath(TelegramBot telegramBot, Sticker sticker) {
        Objects.requireNonNull(sticker);

        GetFile getFileMethod = new GetFile();
        getFileMethod.setFileId(sticker.getFileId());

        try {
            return Optional.ofNullable(telegramBot.getFile(getFileMethod).getFilePath());

        } catch (TelegramApiException e) {
            logger.error("Error while downloading Telegram sticker image: {}", e.toString());
        }

        return Optional.empty();
    }

}
