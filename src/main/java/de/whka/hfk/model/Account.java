package de.whka.hfk.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "owner_id", nullable = false)
    private UUID owner;

    @Column(length = 512)
    private String name;

    @Column(nullable = false)
    private boolean active;

    public Account(UUID owner, String name, boolean active) {
        this.owner = owner;
        this.name = name;
        this.active = active;
    }
}
