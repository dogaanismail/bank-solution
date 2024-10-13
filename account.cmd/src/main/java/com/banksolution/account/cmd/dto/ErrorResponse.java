package com.banksolution.account.cmd.dto;

import com.banksolution.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
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
