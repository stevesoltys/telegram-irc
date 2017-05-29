package com.stevesoltys.telegramirc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Steve Soltys
 */
@Component
public class ConfigurationLoader {

    private static final String DEFAULT_CONFIGURATION_PATH = System.getProperty("user.home") +
            "/.config/telegram-irc/config.json";

    private final ConfigurationEntryRepository configurationRepository;

    @Autowired
    public ConfigurationLoader(ConfigurationEntryRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    public void initialize() {
        initialize(DEFAULT_CONFIGURATION_PATH);
    }

    public void initialize(String configurationPath) {
        File configurationFile = new File(configurationPath);

        StringBuilder builder = new StringBuilder();
        try (Stream<String> lines = Files.lines(Paths.get(configurationFile.toURI()))) {
            lines.forEach(builder::append);

        } catch (FileNotFoundException e) {
            throw new ConfigurationException("Could not find configuration file.");

        } catch (IOException e) {
            throw new ConfigurationException("Could not read configuration file: " + e.toString());
        }

        String configurationString = builder.toString();

        Map<String, Object> configurationMap = new GsonJsonParser().parseMap(configurationString);
        configurationRepository.getAllEntries().forEach(entry -> entry.initialize(configurationMap));
    }
}
