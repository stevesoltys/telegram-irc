package com.stevesoltys.telegramirc.configuration.irc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Steve Soltys
 */
@Configuration
@ConfigurationProperties("irc")
public class IRCConfiguration {

    private String address;

    private int port;

    private String password;

    private boolean ssl;

    private String operatorNick;

    private String operatorPassword;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public String getOperatorNick() {
        return operatorNick;
    }

    public void setOperatorNick(String operatorNick) {
        this.operatorNick = operatorNick;
    }

    public String getOperatorPassword() {
        return operatorPassword;
    }

    public void setOperatorPassword(String operatorPassword) {
        this.operatorPassword = operatorPassword;
    }
}
