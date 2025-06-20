package de.whka.hfk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RegisterDTO(UUID id, @NotBlank @Size(max = 512) String role, @NotBlank @Size(max = 512) String name) {

}
