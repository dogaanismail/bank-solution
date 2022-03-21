package com.bankingsolution.cqrs.core.generics;

public class GenericResponse {
    public static ResponseModel generateErrorResponse() {
        return generateResponse(ResponseStatus.ERROR, null, null);
    }

    public static ResponseModel generateErrorResponse(String message) {
        return generateResponse(ResponseStatus.ERROR, null, message);
    }

    public static ResponseModel generateResponse(ResponseStatus status, Object responseObj) {
        return generateResponse(status, responseObj, null);
    }

    public static ResponseModel generateResponse(ResponseStatus status, Object responseObj, String message) {
        ResponseModel response = new ResponseModel();

        response.setStatus(status);
        response.setMessage(message);
        response.setData(responseObj);

        return response;
    }
}

