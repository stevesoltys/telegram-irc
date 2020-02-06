package com.stevesoltys.telegramirc.protocol.irc.event.handler;

import com.stevesoltys.telegramirc.protocol.irc.IRCMessageEncoder;
import com.stevesoltys.telegramirc.protocol.irc.event.ActionMessageEvent;
import com.stevesoltys.telegramirc.protocol.irc.event.ChannelMessageEvent;
import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBot;
import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBotRepository;
import com.stevesoltys.telegramirc.protocol.telegram.channel.TelegramChannel;
import com.stevesoltys.telegramirc.protocol.telegram.channel.TelegramChannelRepository;
import org.pircbotx.Channel;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

/**
 * @author Steve Soltys
 */
@Component
public class ChannelMessageHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TelegramChannelRepository channelRepository;

    private final TelegramBotRepository telegramBotRepository;

    private final IRCMessageEncoder messageEncoder;

    @Autowired
    public ChannelMessageHandler(TelegramChannelRepository channelRepository,
                                 TelegramBotRepository telegramBotRepository,
                                 IRCMessageEncoder messageEncoder) {

        this.channelRepository = channelRepository;
        this.telegramBotRepository = telegramBotRepository;
        this.messageEncoder = messageEncoder;
    }

    @EventListener
    public void handleChannelActionMessage(ActionMessageEvent actionMessageEvent) {
        ActionEvent event = actionMessageEvent.getEvent();

        if (event.getChannel() == null || event.getUser() == null) {
            return;
        }

        String senderNick = event.getUser().getNick();

        telegramBotRepository.findBot(senderNick).ifPresent(telegramBot ->
                sendChannelMessage(telegramBot, event.getChannel(), event.getMessage(), true));
    }

    @EventListener
    public void handleChannelMessage(ChannelMessageEvent channelMessageEvent) {
        MessageEvent event = channelMessageEvent.getEvent();

        if (event.getUser() == null) {
            return;
        }

        String senderNick = event.getUser().getNick();

        telegramBotRepository.findBot(senderNick).ifPresent(telegramBot ->
                sendChannelMessage(telegramBot, event.getChannel(), event.getMessage(), false));
    }

    private void sendChannelMessage(TelegramBot telegramBot, Channel channel, String message,
                                    boolean action) {
        String channelIdentifier = channel.getName();
        Optional<TelegramChannel> channelOptional = channelRepository.findByIrcIdentifier(channelIdentifier);

        if (channelOptional.isPresent()) {
            String telegramId = channelOptional.get().getTelegramIdentifier();
            SendMessage telegramMessage = messageEncoder.encodeMessage(telegramId, message, channel.getUsers(), action);

            try {
                telegramBot.execute(telegramMessage);

            } catch (TelegramApiException e) {
                logger.error("Error while sending Telegram message: {}", e.toString());
            }

        } else {
            logger.error("Could not find channel for identifier: {}", channelIdentifier);
        }
    }
}
