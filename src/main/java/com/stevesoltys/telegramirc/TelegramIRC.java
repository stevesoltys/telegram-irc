package com.stevesoltys.telegramirc;

import com.stevesoltys.telegramirc.configuration.ConfigurationLoader;
import com.stevesoltys.telegramirc.protocol.irc.IRCProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConfigurationLoader configurationLoader;

    private final IRCProtocol ircProtocol;

    @Autowired
    public TelegramIRC(ConfigurationLoader configurationLoader, IRCProtocol ircProtocol) {
        this.configurationLoader = configurationLoader;
        this.ircProtocol = ircProtocol;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 1) {
            configurationLoader.initialize(args[0]);

        } else if(args.length == 0) {
            configurationLoader.initialize();

        } else {
            logger.error("Usage: telegramirc configuration_path");
            System.exit(1);
        }

        ircProtocol.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(TelegramIRC.class, args);
    }
}
