package com.cps.pwm.repository;

import com.cps.pwm.model.Account;
import com.cps.pwm.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByFromAccount(Account account);
}
