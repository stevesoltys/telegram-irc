package com.stevesoltys.telegramirc.protocol.irc;

import org.pircbotx.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Steve Soltys
 */
@Component
public class IRCMessageEncoder {

    private static final String SPECIAL_CHARACTERS_PATTERN = "([$&+,:;=?@#|<>]*)";

    public SendMessage encodeMessage(String chatId, String message, Set<User> users, boolean action) {
        message = action ? encodeActionMessage(message) : message;
        message = encodeUserHighlights(users, message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        return sendMessage;
    }

    private String encodeActionMessage(String message) {
        return "*" + message + "*";
    }

    private String encodeUserHighlights(Set<User> users, String message) {
        List<String> tokens = Arrays.asList(message.split("\\s"));
        StringBuilder result = new StringBuilder();

        tokens.forEach(token -> {
            for (User user : users) {
                String pattern = SPECIAL_CHARACTERS_PATTERN + user.getNick() + SPECIAL_CHARACTERS_PATTERN;

                if (Pattern.compile(pattern).matcher(token).matches()) {
                    token = token.replaceAll(pattern, "$1@" + user.getNick() + "$2");
                    break;
                }
            }

            result.append(token).append(" ");
        });

        return result.toString();
    }
}
