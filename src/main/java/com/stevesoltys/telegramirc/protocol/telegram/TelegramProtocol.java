package com.stevesoltys.telegramirc.protocol.telegram;

import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBot;
import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBotRepository;
import com.stevesoltys.telegramirc.configuration.TelegramBotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

import java.util.Map;

/**
 * @author Steve Soltys
 */
@Service
public class TelegramProtocol {

    private final ApplicationEventPublisher eventPublisher;

    private final TelegramBotConfiguration botConfiguration;

    private final TelegramBotRepository botRepository;

    private final TelegramBotsApi telegramApi;

    @Autowired
    public TelegramProtocol(ApplicationEventPublisher eventPublisher, TelegramBotConfiguration botConfiguration,
                            TelegramBotRepository botRepository) {
        this.eventPublisher = eventPublisher;
        this.botConfiguration = botConfiguration;
        this.botRepository = botRepository;

        telegramApi = new TelegramBotsApi();
    }

    public void initialize() throws TelegramApiException {

        botConfiguration.getBotEntries().forEach(entry -> {
            TelegramBot telegramBot = new TelegramBot(eventPublisher, entry.getUsername(), entry.getToken());

            botRepository.register(entry.getNick(), telegramBot);
        });

        for (Map.Entry<String, TelegramBot> entry : botRepository.getRepository().entrySet()) {
            telegramApi.registerBot(entry.getValue());
        }
    }

}
