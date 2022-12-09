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
    void insertTransaction(AccountTransaction transaction);

    @Select("SELECT * FROM account_transaction WHERE account_id = #{accountId}")
    @Results(id = "transactionResult", value = {
            @Result(property = "transactionId", column = "transaction_id"),
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "direction", column = "direction"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "status", column = "status"),
            @Result(property = "transactionTime", column = "transaction_time"),
            @Result(property = "description", column = "description"),
            @Result(property = "currency", column = "currency"),
            @Result(property = "balanceAfterTxn", column = "balance_after_txn")
    })
    List<AccountTransaction> getTransactionsByAccountId(@Param("accountId") String accountId);
}
