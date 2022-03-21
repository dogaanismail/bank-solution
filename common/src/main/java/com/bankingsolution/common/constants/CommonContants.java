package com.bankingsolution.common.constants;

public class CommonContants {

    private CommonContants(){

    }

    public static String[] SUPPORTED_CURRENCIES = new String[]{"EUR", "SEK", "GBP", "USD"};

    public static final String AccountOpenedTopic = "AccountOpenedEvent";
    public static final String FundsDepositedTopic = "FundsDepositedEvent";
    public static final String FundsWithDrawnTopic = "FundsWithDrawnEvent";
    public static final String TransactionCreatedTopic = "TransactionCreatedEvent";
    public static final String TOPIC_KEY = "banking.solution.topic.key";
}
