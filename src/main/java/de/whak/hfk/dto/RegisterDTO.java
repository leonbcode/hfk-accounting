package de.whak.hfk.dto;

import de.whak.hfk.model.Register;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RegisterDTO {
    private UUID id;
    private String role;
    private String name;

    public RegisterDTO(Register register) {
        this.id = register.getId();
        this.role = register.getRole();
        this.name = register.getName();
    }

    public Register toEntity() {
        return new Register(role, name);
    }
}
