package com.banksolution.common.validation;

import lombok.experimental.UtilityClass;

import java.util.List;

import static com.banksolution.common.constants.Constants.SUPPORTED_CURRENCIES;

@UtilityClass
public class ValidationHelper {

    public static boolean isCurrencySupported(String currencyCode) {
        return !List.of(SUPPORTED_CURRENCIES).contains(currencyCode);
    }
}
