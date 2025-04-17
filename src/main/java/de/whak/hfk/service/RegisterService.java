package de.whak.hfk.service;

import de.whak.hfk.dto.RegisterDTO;
import de.whak.hfk.model.Register;
import de.whak.hfk.repository.RegisterRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RegisterService {

    private final RegisterRepository registerRepository;

    public RegisterService(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    public RegisterDTO addRegister(RegisterDTO registerDTO) {
        Register register = registerRepository.save(registerDTO.toEntity());
        return new RegisterDTO(register);
    }

    public void deleteRegister(UUID uuid) {
        registerRepository.deleteById(uuid);
    }

    public List<RegisterDTO> getAllRegisters() {
        return registerRepository.findAll().stream().map(RegisterDTO::new).toList();
    }

    public List<RegisterDTO> getAccessibleRegisters(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.replace("ROLE_", ""))
                .map(registerRepository::findByRole)
                .filter(Objects::nonNull)
                .map(RegisterDTO::new)
                .toList();
    }
}
