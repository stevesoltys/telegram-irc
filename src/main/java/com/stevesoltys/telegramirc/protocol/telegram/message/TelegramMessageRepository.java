package com.stevesoltys.telegramirc.protocol.telegram.message;

import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.api.objects.Message;

import java.util.*;

/**
 * @author Steve Soltys
 */
@Repository
public class TelegramMessageRepository {

    private final Map<Long, Set<Integer>> repository = new HashMap<>();

    public boolean add(Message message) {
        return repository.computeIfAbsent(message.getChatId(), (tmp) -> new HashSet<>())
                .add(getMessageIdentifier(message));
    }

    public boolean hasProcessed(Message message) {
        Set<Integer> messageIdentifiers = repository.getOrDefault(message.getChatId(), Collections.emptySet());

        return messageIdentifiers.contains(getMessageIdentifier(message));
    }

    private int getMessageIdentifier(Message message) {
        return message.isSuperGroupMessage() ? message.getMessageId() : message.getDate();
    }
}
