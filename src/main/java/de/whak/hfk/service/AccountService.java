package de.whak.hfk.service;

import de.whak.hfk.dto.AccountDTO;
import de.whak.hfk.model.Account;
import de.whak.hfk.repository.AccountRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO getUserAccount(Jwt jwt) {
        UUID owner = UUID.fromString(jwt.getSubject());
        Account account = accountRepository.findByOwner(owner);
        if (account == null) return null;
        return new AccountDTO(account);
    }

    public List<AccountDTO> getRegisterAccounts(UUID registerId) {
        return accountRepository.findAllByOwner(registerId).stream().map(AccountDTO::new).toList();
    }

    public AccountDTO createAccount(AccountDTO account) {
        return new AccountDTO(accountRepository.save(account.toEntity()));
    }

    public AccountDTO updateAccount(AccountDTO accountDTO) {
        Optional<Account> optionalAccount = accountRepository.findById(accountDTO.getId());

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setOwner(accountDTO.getOwner());
            account.setName(accountDTO.getName());
            account.setActive(accountDTO.isActive());
            return new AccountDTO(accountRepository.save(account));
        }
        return null;
    }

    public void deleteAccount(UUID id) {
        accountRepository.deleteById(id);
    }
}
