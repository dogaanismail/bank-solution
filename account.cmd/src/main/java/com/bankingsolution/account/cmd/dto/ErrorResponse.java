package com.bankingsolution.account.cmd.dto;

import com.bankingsolution.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse extends BaseResponse {
    private String id;

    public ErrorResponse(String message,
                         String id) {
        super(message);
        this.id = id;
    }

}
