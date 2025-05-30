package com.cps.pwm.business;

import com.cps.pwm.dto.AccountDto;
import com.cps.pwm.dto.TransactionDto;
import com.cps.pwm.dto.response.AccountResponse;
import com.cps.pwm.model.Account;
import com.cps.pwm.model.User;
import com.cps.pwm.service.AccountService;
import com.cps.pwm.service.TransactionService;
import com.cps.pwm.service.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountBusiness {
    private final AccountService accountService;
    private final UserService userService;
    private final TransactionService transactionService;

    public AccountBusiness(AccountService accountService, UserService userService, TransactionService transactionService) {
        this.accountService = accountService;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    public boolean createAccount(Long userId, String currency) {
        try {
            User user = userService.getUserById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

            Account account = Account.builder()
                    .user(user)
                    .currency(currency)
                    .build();

            accountService.createAccount(account);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create account", e);
        }
    }

    public AccountResponse findAllAccountByUserId(Long userId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        List<Account> accounts = accountService.getAllAccountsByUser(user);

        List<AccountDto> accountDtos = accounts.stream().map(account -> {
            // Mapping Transaction List
            List<TransactionDto> transactions = transactionService
                    .getAllTransactionByFromAccount(account)
                    .stream()
                    .map(tx -> TransactionDto.builder()
                            .transactionId(tx.getId())
                            .fromAccountId(tx.getFromAccount() != null ? tx.getFromAccount().getId() : null)
                            .toAccountId(tx.getToAccount() != null ? tx.getToAccount().getId() : null)
                            .amount(tx.getAmount())
                            .status(tx.getStatus())
                            .description(tx.getDescription())
                            .createdAt(tx.getCreatedAt().format(formatter))
                            .build())
                    .toList();

            return AccountDto.builder()
                    .accountId(account.getId())
                    .currency(account.getCurrency())
                    .balance(account.getAccountBalance() != null ? account.getAccountBalance().getBalance() : BigDecimal.ZERO)
                    .updateDate(account.getAccountBalance() != null ? account.getAccountBalance().getUpdatedAt().format(formatter) : null)
                    .transactions(transactions)
                    .build();
        }).toList();

        return AccountResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .accounts(accountDtos)
                .build();
    }
}
