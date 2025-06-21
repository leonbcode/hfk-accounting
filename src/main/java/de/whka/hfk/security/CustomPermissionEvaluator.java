package de.whka.hfk.security;

import de.whka.hfk.dto.AccountDTO;
import de.whka.hfk.dto.RegisterDTO;
import de.whka.hfk.service.AccountService;
import de.whka.hfk.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final RegisterService registerService;

    private final AccountService accountService;

    public CustomPermissionEvaluator(RegisterService registerService, AccountService accountService) {
        this.registerService = registerService;
        this.accountService = accountService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || targetDomainObject == null || !(permission instanceof String)) {
            return false;
        }

        if (permission.equals("manage") && targetDomainObject instanceof AccountDTO accountDTO) {
            return registerService.getAccessibleRegisters(authentication).stream()
                    .map(RegisterDTO::id)
                    .anyMatch(accountDTO.owner()::equals);
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (authentication == null || targetType == null || !(permission instanceof String)) {
            return false;
        }

        if (targetType.equals("Account") && permission.equals("manage") && targetId instanceof UUID accountId) {
            AccountDTO account = accountService.getAccount(accountId);
            if (account == null) return false;

            return registerService.getAccessibleRegisters(authentication).stream()
                    .map(RegisterDTO::id)
                    .anyMatch(account.owner()::equals);
        }

        return false;
    }
}
