package com.stevesoltys.telegramirc.configuration;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Steve Soltys
 */
@Repository
public class ConfigurationEntryRepository {

    private final Set<ConfigurationEntry> repository;

    public ConfigurationEntryRepository() {
        repository = new HashSet<>();
    }

    boolean register(ConfigurationEntry configurationEntry) {
        return repository.add(configurationEntry);
    }

    Set<ConfigurationEntry> getAllEntries() {
        return repository;
    }
}
