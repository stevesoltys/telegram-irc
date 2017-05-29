package com.stevesoltys.telegramirc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author Steve Soltys
 */
@Component
public abstract class ConfigurationEntry {

    private final ConfigurationEntryRepository configurationEntryRepository;

    @Autowired
    public ConfigurationEntry(ConfigurationEntryRepository configurationEntryRepository) {
        this.configurationEntryRepository = configurationEntryRepository;
    }

    @PostConstruct
    protected void register() {
        configurationEntryRepository.register(this);
    }

    public abstract void initialize(Map<String, Object> configuration);
}
