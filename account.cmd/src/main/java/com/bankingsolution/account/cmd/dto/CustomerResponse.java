package com.bankingsolution.account.cmd.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
    private Long customerId;
    private String country;
}
