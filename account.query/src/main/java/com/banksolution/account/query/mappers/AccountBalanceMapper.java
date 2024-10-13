package com.banksolution.account.query.mappers;

import com.banksolution.account.query.domain.AccountBalance;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountBalanceMapper {

    @Insert("INSERT INTO account_balance(account_balance_id, account_id, customer_id, currency_code, balance, available_balance) " +
            "VALUES (#{accountBalanceId}, #{accountId}, #{customerId}, #{currencyCode}, #{balance}, #{availableBalance})")
    void insertAccountBalance(AccountBalance balance);

    @Update("UPDATE account_balance SET balance = #{balance} WHERE account_balance_id = #{accountBalanceId}")
    void updateAccountBalance(AccountBalance balance);

    @Select("SELECT * FROM account_balance WHERE account_id = #{accountId} AND currency_code = #{currencyCode}")
    @Results(id = "accountBalanceResult", value = {
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "accountBalanceId", column = "account_balance_id"),
            @Result(property = "currencyCode", column = "currency_code"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "availableBalance", column = "available_balance")
    })
    AccountBalance getBalance(@Param("accountId") String accountId,
                              @Param("currencyCode") String currencyCode
    );

    @Select("SELECT * FROM account_balance where account_id = #{accountId}")
    @Results(id = "balancesResult", value = {
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "accountBalanceId", column = "account_balance_id"),
            @Result(property = "currencyCode", column = "currency_code"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "availableBalance", column = "available_balance")
    })
    List<AccountBalance> getBalancesByAccountId(@Param("accountId") String accountId);
}
