package de.whak.hfk.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionReversal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "original_transaction_id", unique = true, nullable = false)
    private Transaction originalTransaction;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "reversing_transaction_id", unique = true, nullable = false)
    private Transaction reversingTransaction;

    @Column(length = 512, updatable = false)
    private String reason;

    public TransactionReversal(Transaction originalTransaction, Transaction reversingTransaction, String reason) {
        this.originalTransaction = originalTransaction;
        this.reversingTransaction = reversingTransaction;
        this.reason = reason;
    }
}
