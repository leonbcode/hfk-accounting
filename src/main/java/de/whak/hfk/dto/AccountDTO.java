package de.whak.hfk.dto;

import de.whak.hfk.model.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AccountDTO(UUID id, @NotBlank UUID owner, @NotBlank @Size(max = 512) String name,
                         @NotNull boolean active) {

    public AccountDTO(Account account) {
        this(account.getId(), account.getOwner(), account.getName(), account.isActive());
    }

    public Account toEntity() {
        return new Account(owner, name, active);
    }
}
