package com.stevesoltys.telegramirc.configuration.irc;

import com.stevesoltys.telegramirc.configuration.ConfigurationEntry;
import com.stevesoltys.telegramirc.configuration.ConfigurationEntryRepository;
import com.stevesoltys.telegramirc.configuration.ConfigurationException;
import com.stevesoltys.telegramirc.protocol.irc.OperatorBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Steve Soltys
 */
@Component
public class IRCConfiguration extends ConfigurationEntry {

    private static final String IRC_KEY = "irc";

    private static final String ADDRESS_KEY = "address";

    private static final String PORT_KEY = "port";

    private static final String PASSWORD_KEY = "password";

    private static final String SSL_FLAG_KEY = "ssl";

    private static final String OPERATOR_NICK_KEY = "operator_nick";

    private static final String OPERATOR_PASSWORD_KEY = "operator_password";

    private String serverAddress;

    private int serverPort;

    private String serverPassword;

    private boolean ssl;

    private String operatorNick;

    private String operatorPassword;

    @Autowired
    public IRCConfiguration(ConfigurationEntryRepository configurationEntryRepository) {
       super(configurationEntryRepository);
    }

    @SuppressWarnings("unchecked")
    public void initialize(Map<String, Object> ircConfiguration) {
        Map<String, Object> serverConfiguration = (Map<String, Object>) ircConfiguration.get(IRC_KEY);

        if (serverConfiguration == null) {
            throw new ConfigurationException("Could not find 'irc' entry.");
        }

        serverAddress = (String) serverConfiguration.get(ADDRESS_KEY);

        if(serverAddress == null) {
            throw new ConfigurationException("Could not find IRC 'address' entry.");
        }

        serverPort = (int) (double) serverConfiguration.getOrDefault(PORT_KEY, 6667);
        serverPassword = (String) serverConfiguration.get(PASSWORD_KEY);
        ssl = (boolean) serverConfiguration.getOrDefault(SSL_FLAG_KEY, false);

        operatorNick = (String) serverConfiguration.getOrDefault(OPERATOR_NICK_KEY, OperatorBot.DEFAULT_NICK);
        operatorPassword = (String) serverConfiguration.getOrDefault(OPERATOR_PASSWORD_KEY, "");
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getServerPassword() {
        return serverPassword;
    }

    public boolean getSslFlag() {
        return ssl;
    }

    public String getOperatorNick() {
        return operatorNick;
    }

    public String getOperatorPassword() {
        return operatorPassword;
    }
}
