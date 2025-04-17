package de.whak.hfk.repository;

import de.whak.hfk.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RegisterRepository  extends JpaRepository<Register, UUID> {

    Register findByRole(String role);

}
