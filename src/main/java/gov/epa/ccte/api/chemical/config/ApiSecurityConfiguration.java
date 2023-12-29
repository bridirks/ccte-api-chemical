package gov.epa.ccte.api.chemical.config;


import gov.epa.ccte.api.chemical.domain.ApiKey;
import gov.epa.ccte.api.chemical.repository.ApiKeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Profile("apikey")
@Configuration
public class ApiSecurityConfiguration {

    private final ApiKeyRepository repository;

    public ApiSecurityConfiguration(ApiKeyRepository repository) {
        this.repository = repository;
    }

    @Bean
    public ConcurrentHashMap<UUID, String> apiKeyStore(){
        log.debug("*** start filling ApiKeyStore ***");

        ConcurrentHashMap<UUID, String> keyStore = new ConcurrentHashMap<>();

        List<ApiKey> keys = repository.findAll();

        for(ApiKey key : keys)
            keyStore.put(key.getId(), key.getEmail());

        log.info("*** {} keys are loaded. *** ", keys.size());

        return keyStore;
    }
}
