// AccountDto.java
package com.cps.pwm.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
    private Long accountId;
    private String currency;
    private BigDecimal balance;
    private String updateDate;
    private List<TransactionDto> transactions;
}
