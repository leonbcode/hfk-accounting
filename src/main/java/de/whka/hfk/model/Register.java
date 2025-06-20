package de.whka.hfk.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

/**
 * This entity decides if a given group has accounting capabilities.
 */
@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Register {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    @Setter(AccessLevel.NONE)
    UUID id;

    @Column(length = 512, unique = true)
    String role;

    @Column(length = 512)
    private String name;

    public Register(String role, String name) {
        this.role = role;
        this.name = name;
    }
}
