package com.stevesoltys.telegramirc.protocol.irc.event.handler;

import com.stevesoltys.telegramirc.protocol.irc.event.ChannelMessageEvent;
import com.stevesoltys.telegramirc.protocol.telegram.channel.TelegramChannel;
import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBot;
import com.stevesoltys.telegramirc.protocol.telegram.channel.TelegramChannelRepository;
import com.stevesoltys.telegramirc.protocol.telegram.bot.TelegramBotRepository;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Optional;

/**
 * @author Steve Soltys
 */
@Component
public class ChannelMessageHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TelegramChannelRepository channelRepository;

    private final TelegramBotRepository telegramBotRepository;

    @Autowired
    public ChannelMessageHandler(TelegramChannelRepository channelRepository, TelegramBotRepository telegramBotRepository) {
        this.channelRepository = channelRepository;
        this.telegramBotRepository = telegramBotRepository;
    }

    @EventListener
    public void handle(ChannelMessageEvent channelMessageEvent) {
        MessageEvent event = channelMessageEvent.getEvent();

        if (event.getUser() == null) {
            return;
        }

        String nick = event.getUser().getNick();
        Optional<TelegramBot> telegramBotOptional = telegramBotRepository.findBot(nick);

        if (!telegramBotOptional.isPresent()) {
            return;
        }

        TelegramBot telegramBot = telegramBotOptional.get();

        String channelIdentifier = event.getChannel().getName();
        Optional<TelegramChannel> channelOptional = channelRepository.findByIrcIdentifier(channelIdentifier);

        if (channelOptional.isPresent()) {
            TelegramChannel telegramChannel = channelOptional.get();

            SendMessage message = new SendMessage()
                    .setText(event.getMessage())
                    .setChatId(telegramChannel.getTelegramIdentifier());

            try {
                telegramBot.sendMessage(message);

            } catch (TelegramApiException e) {
                logger.error("Error while sending Telegram message: {}", e.toString());
            }

        } else {
            logger.error("Could not find channel for identifier: {}", channelIdentifier);
        }
    }
}
