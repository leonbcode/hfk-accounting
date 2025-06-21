package de.whka.hfk.service;

import de.whka.hfk.model.Account;
import de.whka.hfk.repository.AccountRepository;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserSyncService {

    private static final Logger log = LoggerFactory.getLogger(UserSyncService.class);

    private final AccountRepository accountRepository;

    private final Keycloak keycloak = KeycloakBuilder.builder()
            .serverUrl("http://localhost:8080")
            .realm("hfk")
            .clientId("admin-cli")
            .clientSecret("kZMosKdKNgnkSBgYg3zJUODuXUAdAmrW")
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
            .build();

    public UserSyncService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void syncUsers() {
        log.info("Starting sync users");
        keycloak.realm("hfk").users().list().stream()
                .map(user -> UUID.fromString(user.getId()))
                .forEach(uuid -> {
                    log.info("Syncing user {}", uuid);
                    if (!accountRepository.existsByOwner(uuid)) {
                        accountRepository.save(new Account(uuid, "User Account", true));
                        log.info("Created new account for user {}", uuid);
                    } else {
                        log.info("Account already exists for user {}", uuid);
                    }
                });
    }
}
