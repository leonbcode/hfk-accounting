package de.whak.hfk.dto;

import de.whak.hfk.model.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AccountDTO {
    private UUID id;
    private UUID owner;
    private String name;
    private boolean active;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.owner = account.getOwner();
        this.name = account.getName();
        this.active = account.isActive();
    }

    public Account toEntity() {
        return new Account(owner, name, active);
    }
}
