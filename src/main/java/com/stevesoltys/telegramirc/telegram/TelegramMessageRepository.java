package com.stevesoltys.telegramirc.telegram;

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
        Set<Integer> chatMessages = repository.computeIfAbsent(message.getChatId(), (tmp) -> new HashSet<>());

        if(message.isSuperGroupMessage()) {
            return chatMessages.add(message.getMessageId());

        } else {
            return chatMessages.add(message.getDate());
        }
    }

    public boolean hasProcessed(Message message) {
        Set<Integer> messageIdentifiers = repository.getOrDefault(message.getChatId(), Collections.emptySet());

        return messageIdentifiers.contains(message.isSuperGroupMessage() ? message.getMessageId() : message.getDate());
    }
}
