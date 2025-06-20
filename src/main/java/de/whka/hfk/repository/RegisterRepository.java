package de.whka.hfk.repository;

import de.whka.hfk.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegisterRepository  extends JpaRepository<Register, UUID> {

    Register findByRole(String role);

}
