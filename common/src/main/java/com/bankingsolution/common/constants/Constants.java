package com.bankingsolution.common.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static String[] SUPPORTED_CURRENCIES = new String[]{"EUR", "SEK", "GBP", "USD"};
    public static final String AccountOpenedTopic = "AccountOpenedEvent";
    public static final String FundsDepositedTopic = "FundsDepositedEvent";
    public static final String FundsWithDrawnTopic = "FundsWithDrawnEvent";
    public static final String TransactionCreatedTopic = "TransactionCreatedEvent";
    public static final String TransactionFailedTopic = "TransactionFailedEvent";
    public static final String TOPIC_KEY = "banking.solution.topic.key";
}
