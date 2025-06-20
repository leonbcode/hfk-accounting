package de.whka.hfk.service;

import de.whka.hfk.dto.RegisterDTO;
import de.whka.hfk.mapper.RegisterMapper;
import de.whka.hfk.model.Register;
import de.whka.hfk.repository.RegisterRepository;
import de.whka.hfk.util.AuthenticationUtil;
import org.springframework.security.core.Authentication;
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
        Register register = RegisterMapper.toEntity(registerDTO);
        register = registerRepository.save(register);
        return RegisterMapper.toDTO(register);
    }

    public void deleteRegister(UUID uuid) {
        registerRepository.deleteById(uuid);
    }

    public List<RegisterDTO> getAllRegisters() {
        return registerRepository.findAll().stream().map(RegisterMapper::toDTO).toList();
    }

    public List<RegisterDTO> getAccessibleRegisters(Authentication authentication) {
        return AuthenticationUtil.getRoles(authentication).stream()
                .map(registerRepository::findByRole)
                .filter(Objects::nonNull)
                .map(RegisterMapper::toDTO)
                .toList();
    }
}
