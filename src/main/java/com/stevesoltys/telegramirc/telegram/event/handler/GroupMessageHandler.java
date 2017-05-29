package com.stevesoltys.telegramirc.telegram.event.handler;

import com.stevesoltys.telegramirc.irc.IRCProtocol;
import com.stevesoltys.telegramirc.telegram.TelegramMessageRepository;
import com.stevesoltys.telegramirc.telegram.TelegramMessageDecoder;
import com.stevesoltys.telegramirc.telegram.configuration.TelegramChannelConfiguration;
import com.stevesoltys.telegramirc.telegram.event.TelegramGroupMessageEvent;
import com.stevesoltys.telegramirc.telegram.user.TelegramUser;
import com.stevesoltys.telegramirc.telegram.user.TelegramUserRepository;
import org.pircbotx.PircBotX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;

/**
 * @author Steve Soltys
 */
@Component
public class GroupMessageHandler {

    private final TelegramChannelConfiguration channelConfiguration;

    private final TelegramUserRepository userRepository;

    private final IRCProtocol ircProtocol;

    private final TelegramMessageDecoder messageDecoder;

    private final TelegramMessageRepository messageRepository;

    @Autowired
    public GroupMessageHandler(TelegramChannelConfiguration channelConfiguration, TelegramUserRepository userRepository,
                               IRCProtocol ircProtocol, TelegramMessageDecoder messageDecoder,
                               TelegramMessageRepository messageRepository) {

        this.channelConfiguration = channelConfiguration;
        this.userRepository = userRepository;
        this.ircProtocol = ircProtocol;
        this.messageDecoder = messageDecoder;
        this.messageRepository = messageRepository;
    }

    @EventListener
    public void handle(TelegramGroupMessageEvent event) {
        Message telegramMessage = event.getMessage();

        if (telegramMessage.getFrom() == null || messageRepository.hasProcessed(telegramMessage)) {
            return;
        }

        messageRepository.add(telegramMessage);
        Chat chat = telegramMessage.getChat();

        String telegramChannelIdentifier = String.valueOf(chat.getId());
        String ircChannelIdentifier = "#" + channelConfiguration.getChannelIdentifier(telegramChannelIdentifier);
        ircProtocol.registerChannel(telegramChannelIdentifier, ircChannelIdentifier);

        String telegramIdentifier = String.valueOf(telegramMessage.getFrom().getId());
        String ircIdentifier = telegramMessage.getFrom().getUserName();
        TelegramUser user = getUser(telegramIdentifier, ircIdentifier, ircChannelIdentifier);

        messageDecoder.decode(telegramMessage).forEach(message ->
                user.sendChannelMessage(ircChannelIdentifier, message));
    }

    private TelegramUser getUser(String telegramIdentifier, String ircIdentifier, String ircChannelIdentifier) {
        TelegramUser user = userRepository.findOrRegister(telegramIdentifier, ircIdentifier);
        PircBotX bot = user.getIrcBot();

        boolean inChannel = bot.getUserBot().getChannels().stream()
                .anyMatch(channel -> ircChannelIdentifier.equals(channel.getName()));

        if (!inChannel) {
            user.joinChannel(ircChannelIdentifier);
        }

        return user;
    }
}
