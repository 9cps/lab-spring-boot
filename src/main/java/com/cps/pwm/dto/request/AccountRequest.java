package com.cps.pwm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {

    @NotBlank(message = "Id is required")
    @Size(max = 8)
    private Long userId;

    @NotBlank(message = "Currency is required")
    @Size(max = 3, message = "Currency must not 3 characters Ex. THB")
    private String currency;
}
