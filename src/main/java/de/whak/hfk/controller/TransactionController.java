package de.whak.hfk.controller;

import de.whak.hfk.dto.TransactionDTO;
import de.whak.hfk.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Create transaction
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionDTO));
    }

    // Reverse transaction
    @PostMapping("/reverse/{transactionId}")
    public ResponseEntity<TransactionDTO> reverseTransaction(@PathVariable UUID transactionId) {
        return ResponseEntity.ok(transactionService.reverseTransaction(transactionId));
    }

    // Various endpoints to query transactions

}
