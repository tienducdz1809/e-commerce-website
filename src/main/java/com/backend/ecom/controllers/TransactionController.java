package com.backend.ecom.controllers;

import com.backend.ecom.dto.transaction.TransactionRequestDTO;
import com.backend.ecom.entities.Transaction;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.services.TransactionService;
import com.backend.ecom.supporters.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@PreAuthorize("hasRole('ROLE_USER')")
public class TransactionController {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ResponseObject> getTransactionDetail(@PathVariable("id") Long id) {
        return transactionService.getTransactionDetail(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ResponseObject> newTransaction(@RequestBody TransactionRequestDTO transactionRequest) {
        return transactionService.createTransaction(transactionRequest);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> updateTransaction(@PathVariable("id") Long id, @RequestBody TransactionRequestDTO transactionRequest) {
        return transactionService.updateTransaction(id, transactionRequest);
    }

    @PatchMapping("/payment/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> updateStatus(@PathVariable("id") Long id, @RequestParam TransactionStatus transactionStatus) {
        return transactionService.updatePayment(id, transactionStatus);
    }

}
