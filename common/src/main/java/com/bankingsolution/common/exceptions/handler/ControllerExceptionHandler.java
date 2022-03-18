package com.bankingsolution.common.exceptions.handler;

import com.bankingsolution.common.exceptions.AggregateNotFoundException;
import com.bankingsolution.common.exceptions.model.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> illegalStateException(IllegalStateException e) {
        ExceptionResponse err = new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AggregateNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> illegalStateException(AggregateNotFoundException e) {
        ExceptionResponse err = new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> exception(Exception e) {
        ExceptionResponse err = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}
