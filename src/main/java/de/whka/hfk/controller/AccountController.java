package de.whka.hfk.controller;

import de.whka.hfk.dto.AccountDTO;
import de.whka.hfk.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<AccountDTO> getUserAccount(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(accountService.getUserAccount(jwt));
    }

    @GetMapping("/{registerId}")
    public ResponseEntity<List<AccountDTO>> getRegisterAccounts(@PathVariable UUID registerId) {
        return ResponseEntity.ok(accountService.getRegisterAccounts(registerId));
    }

    @PostMapping
    @PreAuthorize("hasPermission(#account, 'manage')")
    public ResponseEntity<AccountDTO> addAccount(@Validated @RequestBody AccountDTO account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @PutMapping
    @PreAuthorize("hasPermission(#account, 'manage')")
    public ResponseEntity<AccountDTO> updateAccount(@Validated @RequestBody AccountDTO account) {
        return ResponseEntity.ok(accountService.updateAccount(account));
    }

    @DeleteMapping("/{accountId}")
    @PreAuthorize("hasPermission(#accountId, 'Account','manage')")
    public ResponseEntity<Void> removeAccount(@PathVariable UUID accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok().build();
    }

}
