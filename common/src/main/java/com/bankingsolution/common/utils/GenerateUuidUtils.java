package com.bankingsolution.common.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class GenerateUuidUtils {

    public static UUID generateUuid() {
        return UUID.randomUUID();
    }
}
