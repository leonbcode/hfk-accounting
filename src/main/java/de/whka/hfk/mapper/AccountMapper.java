package de.whka.hfk.mapper;

import de.whka.hfk.dto.AccountDTO;
import de.whka.hfk.model.Account;

public final class AccountMapper {

    public static AccountDTO toDTO(Account entity) {
        return new AccountDTO(entity.getId(), entity.getOwner(), entity.getName(), entity.isActive());
    }

    public static Account toEntity(AccountDTO dto){
        return new Account(dto.owner(), dto.name(), dto.active());
    }

}
