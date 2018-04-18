package com.stevesoltys.telegramirc.protocol.telegram.message;

import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBot;
import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.FileDecoder;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Steve Soltys
 */
public abstract class TelegramFileMessageDecoder {

    private static final String TELEGRAM_BOT_FILE_ENDPOINT = "https://api.telegram.org/file/bot";

    private final Logger logger = Logger.getLogger(this.getClass());

    List<String> decode(TelegramBot telegramBot, FileDecoder fileDecoder, String fileId) {
        Optional<File> fileOptional = getFile(telegramBot, fileId);

        if(fileOptional.isPresent()) {
            File file = fileOptional.get();
            String url = getFileUrl(telegramBot, file);

            if (file.getFileSize() < fileDecoder.maxFileSize()) {
                Optional<String> resultUrlOptional = fileDecoder.decode(url);

                if (resultUrlOptional.isPresent()) {
                    return Collections.singletonList(resultUrlOptional.get());
                }
            }
        }

        return Collections.emptyList();
    }

    private Optional<File> getFile(TelegramBot telegramBot, String fileId) {
        GetFile getFileMethod = new GetFile();
        getFileMethod.setFileId(fileId);

        try {
            return Optional.ofNullable(telegramBot.getFile(getFileMethod));

        } catch (TelegramApiException e) {
            logger.error("Error while downloading Telegram sticker image", e);
        }

        return Optional.empty();
    }

    private String getFileUrl(TelegramBot telegramBot, File file) {
        return TELEGRAM_BOT_FILE_ENDPOINT + telegramBot.getBotToken() + "/" + file.getFilePath();
    }

    abstract List<String> decode(TelegramBot telegramBot, Message message);
}
