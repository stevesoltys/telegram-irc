package com.stevesoltys.telegramirc.protocol.telegram.message;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBot;
import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.FileDecoder;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Steve Soltys
 */
public abstract class TelegramFileMessageDecoder {

    private static final String TELEGRAM_BOT_FILE_ENDPOINT = "https://api.telegram.org/file/bot";

    private final Logger logger = Logger.getLogger(this.getClass());

    private final Map<FileDecoder, LoadingCache<String, String>> fileCache = new HashMap<>();

    List<String> decode(TelegramBot telegramBot, FileDecoder fileDecoder, String fileId) {

        LoadingCache<String, String> loadingCache = fileCache.computeIfAbsent(fileDecoder, decoder ->
                CacheBuilder.newBuilder()
                        .maximumSize(10000)
                        .expireAfterWrite(1, TimeUnit.DAYS)
                        .build(new CacheLoader<String, String>() {

                            public String load(String fileId) {
                                Optional<File> fileOptional = getFile(telegramBot, fileId);

                                if(fileOptional.isPresent()){
                                    File file = fileOptional.get();
                                    String url = getFileUrl(telegramBot, file);

                                    if (file.getFileSize() < fileDecoder.maxFileSize()) {
                                        return fileDecoder.decode(url).orElse(null);
                                    }
                                }

                                return null;
                            }
                        }));

        try {
            String result = loadingCache.get(fileId);

            if (result != null) {
                return Collections.singletonList(result);
            }

        } catch (ExecutionException e) {
            logger.error(e);
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
