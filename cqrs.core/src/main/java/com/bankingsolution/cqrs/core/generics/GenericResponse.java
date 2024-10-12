package com.bankingsolution.cqrs.core.generics;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GenericResponse {

    public static ResponseModel generateResponse(
            ResponseStatus status,
            Object responseObj) {

        return generateResponse(status,
                responseObj,
                null
        );
    }

    public static ResponseModel generateResponse(
            ResponseStatus status,
            Object responseObj,
            String message) {

        return ResponseModel.builder()
                .status(status)
                .message(message)
                .data(responseObj)
                .build();
    }
}


