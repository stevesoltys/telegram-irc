package com.stevesoltys.telegramirc.configuration;

/**
 * An exception that occurs when there is an error while loading the configuration.
 *
 * @author Steve Soltys
 */
public class ConfigurationException extends RuntimeException {

    public ConfigurationException(String cause) {
        super("Error while loading configuration: " + cause);
    }
}