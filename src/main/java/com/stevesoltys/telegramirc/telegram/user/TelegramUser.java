package com.stevesoltys.telegramirc.telegram.user;

import org.pircbotx.PircBotX;

import java.util.*;

/**
 * @author Steve Soltys
 */
public class TelegramUser {

    private final String telegramIdentifier;

    private final PircBotX ircBot;

    private final Map<String, List<String>> pendingPrivateMessages;

    private final Map<String, List<String>> pendingChannelMessages;

    public TelegramUser(String telegramIdentifier, PircBotX ircBot) {
        this.telegramIdentifier = telegramIdentifier;
        this.ircBot = ircBot;

        pendingPrivateMessages = new HashMap<>();
        pendingChannelMessages = new HashMap<>();
    }

    public void joinChannel(String channel) {
        if (ircBot.isConnected()) {
            ircBot.sendIRC().joinChannel(channel);

        } else {
            pendingChannelMessages.computeIfAbsent(channel, (tmp) -> new LinkedList<>());
        }
    }

    public void sendChannelMessage(String channel, String message) {
        boolean inChannel = ircBot.getUserBot().getChannels().stream()
                .anyMatch(joinedChannel -> channel.equals(joinedChannel.getName()));

        if (ircBot.isConnected() && inChannel) {
            ircBot.sendIRC().message(channel, message);

        } else {
            pendingChannelMessages.computeIfAbsent(channel, (tmp) -> new LinkedList<>()).add(message);
        }
    }

    public void sendPrivateMessage(String username, String message) {
        if (ircBot.isConnected()) {
            ircBot.sendIRC().message(username, message);

        } else {
            pendingChannelMessages.computeIfAbsent(username, (tmp) -> new LinkedList<>()).add(message);
        }
    }

    public List<String> removePendingChannelMessages(String channel) {
        List<String> messages = pendingChannelMessages.getOrDefault(channel, Collections.emptyList());
        pendingChannelMessages.remove(channel);

        return messages;
    }

    public Map<String, List<String>> removePendingPrivateMessages() {
        Map<String, List<String>> results = new HashMap<>(pendingPrivateMessages);
        pendingPrivateMessages.clear();

        return results;
    }

    public Set<String> getPendingChannels() {
        return pendingChannelMessages.keySet();
    }

    public PircBotX getIrcBot() {
        return ircBot;
    }

    public String getTelegramIdentifier() {
        return telegramIdentifier;
    }

}
