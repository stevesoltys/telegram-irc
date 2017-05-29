package com.stevesoltys.telegramirc.telegram.user;

import com.stevesoltys.telegramirc.irc.IRCProtocol;
import org.pircbotx.PircBotX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Steve Soltys
 */
@Repository
public class TelegramUserRepository {

    private final IRCProtocol ircProtocol;

    private final Set<TelegramUser> userRepository;

    @Autowired
    public TelegramUserRepository(IRCProtocol ircProtocol) {
        this.ircProtocol = ircProtocol;

        userRepository = new HashSet<>();
    }

    public TelegramUser findOrRegister(String telegramIdentifier, String ircIdentifier) {
        Optional<TelegramUser> telegramUserOptional = findByTelegramIdentifier(telegramIdentifier);

        if(telegramUserOptional.isPresent()) {
            return telegramUserOptional.get();
        }

        PircBotX bot = ircProtocol.createBot(ircIdentifier);
        TelegramUser user = new TelegramUser(telegramIdentifier, bot);
        register(user);

        return user;
    }

    public Optional<TelegramUser> findByIrcIdentifier(String nick) {
        return userRepository.stream().filter(user -> user.getIrcBot().getNick().equals(nick)).findAny();
    }

    private Optional<TelegramUser> findByTelegramIdentifier(String identifier) {
        return userRepository.stream().filter(user -> user.getTelegramIdentifier().equals(identifier)).findAny();
    }

    private void register(TelegramUser user) {
        userRepository.add(user);
    }
}
