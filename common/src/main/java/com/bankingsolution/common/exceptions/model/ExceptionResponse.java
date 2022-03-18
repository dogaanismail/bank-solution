package com.bankingsolution.common.exceptions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private Integer code;
    private String status;
    private String description;
    private String date;

    public ExceptionResponse(HttpStatus httpStatus, String description) {
        this.code = httpStatus.value();
        this.status = httpStatus.getReasonPhrase();
        this.description = description;
        this.date = LocalDateTime.now().toString();
    }

    public ExceptionResponse(HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.status = httpStatus.getReasonPhrase();
        this.date = LocalDateTime.now().toString();
    }
}
