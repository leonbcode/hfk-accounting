package de.whak.hfk.controller;

import de.whak.hfk.dto.AccountDTO;
import de.whak.hfk.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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

    // Get user account
    @GetMapping
    public ResponseEntity<AccountDTO> getUserAccount(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(accountService.getUserAccount(jwt));
    }

    // Get accounts of registers
    @GetMapping("/{registerId}")
    public ResponseEntity<List<AccountDTO>> getRegisterAccounts(@PathVariable UUID registerId) {
        return ResponseEntity.ok(accountService.getRegisterAccounts(registerId));
    }

    // Create account (for register) -> /account/uuid  (register UUID)
    @PostMapping
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    // Edit account (of register) -> /account/uuid (account UUID)
    @PutMapping()
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO account) {
        return ResponseEntity.ok(accountService.updateAccount(account));
    }

    // Delete account (of register) -> /account/uuid (account UUID)
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> removeAccount(@PathVariable UUID accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok().build();
    }

}
