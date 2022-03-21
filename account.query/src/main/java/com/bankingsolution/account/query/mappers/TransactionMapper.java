package com.bankingsolution.account.query.mappers;

import com.bankingsolution.account.query.domain.AccountTransaction;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TransactionMapper {

    @Insert("INSERT INTO account_transaction (transaction_id, account_id, direction, amount, status, transaction_time, description, currency, balance_after_txn) " +
            "VALUES (#{transactionId}, #{accountId}, #{direction}," +
            " #{amount}, #{status}, #{transactionTime}, #{description}, #{currency}, #{balanceAfterTxn})")
    @Options(useGeneratedKeys = true, keyProperty = "transactionId", keyColumn = "transaction_id")
    int insertTransaction(AccountTransaction transaction);

    @Select("SELECT * FROM account_transaction WHERE account_id= #{accountId}")
    List<AccountTransaction> getTransactionsByAccountId(@Param("accountId") String accountId);
}
