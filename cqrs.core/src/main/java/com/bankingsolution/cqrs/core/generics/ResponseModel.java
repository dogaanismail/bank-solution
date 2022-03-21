package com.bankingsolution.cqrs.core.generics;

import java.io.Serializable;

public class ResponseModel implements Serializable {

    private ResponseStatus status;
    private Object data;
    private String message;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
