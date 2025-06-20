package de.whka.hfk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AccountDTO(UUID id, @NotNull UUID owner, @NotBlank @Size(max = 512) String name,
                         @NotNull boolean active) {
}
