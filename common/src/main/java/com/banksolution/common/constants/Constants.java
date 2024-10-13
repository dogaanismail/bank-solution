package com.banksolution.common.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static String[] SUPPORTED_CURRENCIES = new String[]{"EUR", "SEK", "GBP", "USD"};
    public static final String TOPIC_KEY = "banking.solution.topic.key";
    public static final String ACCOUNT_OPENED_EVENT_TOPIC = "AccountOpenedEvent";
    public static final String FUNDS_DEPOSITED_EVENT_TOPIC = "FundsDepositedEvent";
    public static final String FUNDS_WITH_DRAWN_EVENT_TOPIC = "FundsWithDrawnEvent";
    public static final String TRANSACTION_CREATED_EVENT_TOPIC = "TransactionCreatedEvent";
    public static final String TRANSACTION_FAILED_EVENT_TOPIC = "TransactionFailedEvent";
}
