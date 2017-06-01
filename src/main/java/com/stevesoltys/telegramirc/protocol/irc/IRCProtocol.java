package com.stevesoltys.telegramirc.protocol.irc;

import com.stevesoltys.telegramirc.configuration.IRCConfiguration;
import com.stevesoltys.telegramirc.protocol.telegram.channel.TelegramChannel;
import com.stevesoltys.telegramirc.protocol.telegram.channel.TelegramChannelRepository;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Steve Soltys
 */
@Service
public class IRCProtocol {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IRCConfiguration serverConfiguration;

    private final OperatorBot operatorBotListener;

    private final UserBot userBotListener;

    private final TelegramChannelRepository telegramChannelRepository;

    private final ExecutorService executorService;

    private PircBotX operatorBot;

    @Autowired
    public IRCProtocol(IRCConfiguration serverConfiguration, OperatorBot operatorBotListener,
                       UserBot userBotListener, TelegramChannelRepository telegramChannelRepository) {

        this.serverConfiguration = serverConfiguration;
        this.operatorBotListener = operatorBotListener;
        this.userBotListener = userBotListener;
        this.telegramChannelRepository = telegramChannelRepository;

        executorService = Executors.newCachedThreadPool();
    }

    public void start() {
        operatorBot = createBot(serverConfiguration.getOperatorNick(), true);
    }

    public PircBotX createBot(String username) {
        return createBot(username, false);
    }

    private PircBotX createBot(String username, boolean operator) {

        Configuration.Builder configurationBuilder = new Configuration.Builder()
                .setName(username)
                .setLogin(username)
                .setRealName(username)
                .addListener(operator ? operatorBotListener : userBotListener)
                .setAutoNickChange(true)
                .setAutoReconnect(true)
                .addServer(serverConfiguration.getServerAddress(), serverConfiguration.getServerPort())
                .setServerPassword(serverConfiguration.getServerPassword());

        if (serverConfiguration.getSslFlag()) {
            configurationBuilder.setSocketFactory(SSLSocketFactory.getDefault());
        }

        PircBotX bot = new PircBotX(configurationBuilder.buildConfiguration());

        executorService.submit(() -> {

            try {
                bot.startBot();

            } catch (IOException | IrcException e) {
                logger.error("Error while starting IRC bot: {}", e.toString());
            }
        });

        return bot;
    }

    public void registerChannel(String telegramChannelIdentifier, String ircChannelIdentifier) {
        Optional<TelegramChannel> targetOptional = telegramChannelRepository.
                findByTelegramIdentifier(telegramChannelIdentifier);

        if (!targetOptional.isPresent()) {
            telegramChannelRepository.register(new TelegramChannel(ircChannelIdentifier, telegramChannelIdentifier));

            operatorBot.sendIRC().joinChannel(ircChannelIdentifier);
        }
    }
}
