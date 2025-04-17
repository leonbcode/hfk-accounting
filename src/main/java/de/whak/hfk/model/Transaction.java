package de.whak.hfk.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime timestamp;

    @Column(updatable = false, length = 512)
    private String description;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TransactionLine> lines;

    public Transaction(String description, List<TransactionLine> lines) {
        this.description = description;
        this.lines = lines;
    }
}
