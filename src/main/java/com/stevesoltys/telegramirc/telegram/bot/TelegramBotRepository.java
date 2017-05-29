package com.stevesoltys.telegramirc.telegram.bot;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Steve Soltys
 */
@Repository
public class TelegramBotRepository {

    private final Map<String, TelegramBot> repository = new HashMap<>();

    public boolean register(String ircIdentifier, TelegramBot telegramBot) {

        if (repository.containsKey(ircIdentifier)) {
            return false;
        }

        repository.put(ircIdentifier, telegramBot);
        return true;
    }

    public Optional<TelegramBot> findBot(String ircIdentifier) {
        return Optional.ofNullable(repository.get(ircIdentifier));
    }

    public Optional<String> findIrcIdentifier(TelegramBot telegramBot) {
        return repository.entrySet().stream()
                .filter(entry -> entry.getValue().equals(telegramBot))
                .map(Map.Entry::getKey)
                .findAny();
    }

    public Map<String, TelegramBot> getRepository() {
        return repository;
    }
}
