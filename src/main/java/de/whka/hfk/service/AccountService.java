package de.whka.hfk.service;

import de.whka.hfk.dto.AccountDTO;
import de.whka.hfk.mapper.AccountMapper;
import de.whka.hfk.model.Account;
import de.whka.hfk.repository.AccountRepository;
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

    public AccountDTO getAccount(UUID id) {
        return accountRepository.findById(id).map(AccountMapper::toDTO).orElse(null);
    }

    public AccountDTO getUserAccount(Jwt jwt) {
        UUID owner = UUID.fromString(jwt.getSubject());
        Account account = accountRepository.findByOwner(owner);
        if (account == null) return null;
        return AccountMapper.toDTO(account);
    }

    public List<AccountDTO> getRegisterAccounts(UUID registerId) {
        return accountRepository.findAllByOwner(registerId).stream().map(AccountMapper::toDTO).toList();
    }

    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = AccountMapper.toEntity(accountDTO);
        account = accountRepository.save(account);
        return AccountMapper.toDTO(account);
    }

    public AccountDTO updateAccount(AccountDTO accountDTO) {
        Optional<Account> optionalAccount = accountRepository.findById(accountDTO.id());

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setOwner(accountDTO.owner());
            account.setName(accountDTO.name());
            account.setActive(accountDTO.active());
            account = accountRepository.save(account);
            return AccountMapper.toDTO(account);
        }
        return null;
    }

    public void deleteAccount(UUID id) {
        accountRepository.deleteById(id);
    }
}
