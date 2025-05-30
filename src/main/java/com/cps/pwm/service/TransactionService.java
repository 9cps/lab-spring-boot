package com.cps.pwm.service;

import com.cps.pwm.model.Account;
import com.cps.pwm.model.Transaction;
import com.cps.pwm.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Create
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Read - All
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Read - By ID
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    // Read - By From Account
    public List<Transaction> getAllTransactionByFromAccount(Account account) {
        return transactionRepository.findAllByFromAccount(account);
    }

    // Update
    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transaction.setFromAccount(updatedTransaction.getFromAccount());
                    transaction.setToAccount(updatedTransaction.getToAccount());
                    transaction.setAmount(updatedTransaction.getAmount());
                    transaction.setStatus(updatedTransaction.getStatus());
                    transaction.setDescription(updatedTransaction.getDescription());
                    transaction.setCreatedAt(updatedTransaction.getCreatedAt());
                    return transactionRepository.save(transaction);
                })
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
    }

    // Delete
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
