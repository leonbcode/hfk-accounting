package de.whak.hfk.repository;

import de.whak.hfk.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsByOwner(UUID owner);

    Account findByOwner(UUID role);

    List<Account> findAllByOwner(UUID owner);

}
