package de.whak.hfk.dto;

import de.whak.hfk.model.Register;

import java.util.UUID;

public record RegisterDTO(UUID id, String role, String name) {

    public RegisterDTO(Register register) {
        this(register.getId(), register.getRole(), register.getName());
    }

    public Register toEntity() {
        return new Register(role, name);
    }
}
