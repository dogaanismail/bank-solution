package com.bankingsolution.cqrs.core.generics;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ResponseModel implements Serializable {

    private ResponseStatus status;
    private Object data;
    private String message;
}
