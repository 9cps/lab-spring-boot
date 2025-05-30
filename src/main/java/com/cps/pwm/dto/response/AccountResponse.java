package com.cps.pwm.dto.response;

import com.cps.pwm.dto.AccountDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse
{
    private Long userId;
    private String email;
    private  String name;
    private List<AccountDto> accounts;

}