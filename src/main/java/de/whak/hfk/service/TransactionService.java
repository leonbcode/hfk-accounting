package de.whak.hfk.service;

import de.whak.hfk.dto.TransactionDTO;
import de.whak.hfk.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        return null;
    }

    public TransactionDTO reverseTransaction(UUID transactionId) {
        return null;
    }
}
