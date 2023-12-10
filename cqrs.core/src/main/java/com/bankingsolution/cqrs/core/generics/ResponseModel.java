package com.bankingsolution.cqrs.core.generics;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ResponseModel implements Serializable {

    private ResponseStatus status;
    private Object data;
    private String message;

}
