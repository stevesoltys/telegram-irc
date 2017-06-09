package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.photo;

import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBot;
import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.photo.imgur.ImgurUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;

import java.util.*;

/**
 * @author Steve Soltys
 */
@Component
public class TelegramPhotoMessageDecoder {

    private static final String TELEGRAM_BOT_FILE_ENDPOINT = "https://api.telegram.org/file/bot";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ImgurUploader imgurUploader;

    @Autowired
    public TelegramPhotoMessageDecoder(ImgurUploader imgurUploader) {
        this.imgurUploader = imgurUploader;
    }

    public List<String> decodePhotoMessage(TelegramBot telegramBot, Message message) {
        Optional<String> filePathOptional = getFilePath(telegramBot, getPhoto(message));

        if (filePathOptional.isPresent()) {
            String telegramBotToken = telegramBot.getBotToken();
            String filePath = filePathOptional.get();

            String url = TELEGRAM_BOT_FILE_ENDPOINT + telegramBotToken + "/" + filePath;
            Optional<String> result = imgurUploader.upload(url);

            if (result.isPresent()) {
                return Collections.singletonList(result.get());
            }
        }

        return Collections.emptyList();
    }

    private Optional<String> getFilePath(TelegramBot telegramBot, PhotoSize photo) {
        Objects.requireNonNull(photo);

        if (photo.getFilePath() != null) {
            return Optional.of(photo.getFilePath());

        } else {
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(photo.getFileId());

            try {
                return Optional.ofNullable(telegramBot.getFile(getFileMethod).getFilePath());

            } catch (TelegramApiException e) {
                logger.error("Error while downloading Telegram photo: {}", e.toString());
            }
        }

        return Optional.empty();
    }

    private PhotoSize getPhoto(Message message) {
        List<PhotoSize> photos = message.getPhoto();

        // Take the largest photo
        return photos.stream()
                .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                .findFirst()
                .orElse(null);
    }
}
