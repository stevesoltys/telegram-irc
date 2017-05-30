package com.stevesoltys.telegramirc.protocol.telegram.channel;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Steve Soltys
 */
@Repository
public class TelegramChannelRepository {

    private final Set<TelegramChannel> messageTargetRepository = new HashSet<>();

    public Optional<TelegramChannel> findByIrcIdentifier(String identifier) {

        return messageTargetRepository.stream()
                .filter(telegramChannel -> telegramChannel.getIrcIdentifier().equals(identifier))
                .findAny();
    }

    public Optional<TelegramChannel> findByTelegramIdentifier(String identifier) {

        return messageTargetRepository.stream()
                .filter(telegramChannel -> telegramChannel.getTelegramIdentifier().equals(identifier))
                .findAny();
    }

    public boolean register(TelegramChannel telegramChannel) {
        return messageTargetRepository.add(telegramChannel);
    }
}
