package de.whak.hfk.security;

import de.whak.hfk.dto.AccountDTO;
import de.whak.hfk.dto.RegisterDTO;
import de.whak.hfk.service.AccountService;
import de.whak.hfk.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private AccountService accountService;

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
