package com.bankingsolution.common.validation;

import java.util.List;

import static com.bankingsolution.common.constants.Constants.SUPPORTED_CURRENCIES;

public class ValidationHelper {
    public static boolean isCurrencySupported(String currencyCode) {
        return !List.of(SUPPORTED_CURRENCIES).contains(currencyCode);
    }
}
