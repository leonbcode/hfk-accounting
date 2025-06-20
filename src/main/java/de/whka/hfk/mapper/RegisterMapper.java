package de.whka.hfk.mapper;

import de.whka.hfk.dto.RegisterDTO;
import de.whka.hfk.model.Register;

public final class RegisterMapper {

    public static RegisterDTO toDTO(Register entity) {
        return new RegisterDTO(entity.getId(), entity.getRole(), entity.getName());
    }

    public static Register toEntity(RegisterDTO dto) {
        return new Register(dto.role(), dto.name());
    }
}
