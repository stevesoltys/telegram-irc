package com.stevesoltys.telegramirc;

import com.stevesoltys.telegramirc.protocol.irc.IRCProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Steve Soltys
 */
@SpringBootApplication
@EnableAutoConfiguration
public class TelegramIRC implements CommandLineRunner {

    private final IRCProtocol ircProtocol;

    @Autowired
    public TelegramIRC(IRCProtocol ircProtocol) {
        this.ircProtocol = ircProtocol;
    }

    @Override
    public void run(String... args) {
        ircProtocol.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(TelegramIRC.class, args);
    }
}
