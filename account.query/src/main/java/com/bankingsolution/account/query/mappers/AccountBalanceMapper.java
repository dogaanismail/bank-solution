package com.bankingsolution.account.query.mappers;

import com.bankingsolution.account.query.domain.AccountBalance;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AccountBalanceMapper {

    @Insert("INSERT INTO account_balance(account_balance_id, account_id, customer_id, currency_code, balance, available_balance) " +
            "VALUES (#{accountBalanceId}, #{accountId}, #{customerId}, #{currencyCode}, #{balance}, #{availableBalance})")
    int insertAccountBalance(AccountBalance balance);

    @Update("UPDATE account_balance SET amount = #{amount} WHERE account_balance_id = #{accountBalanceId}")
    int updateAccountBalance(AccountBalance balance);

    @Select("SELECT * FROM account_balance where customer_id = #{customerId}")
    List<AccountBalance> getBalanceByCustomerId(@Param("customerId") Long customerId);

    @Select("SELECT * FROM account_balance WHERE account_id = #{accountID} AND currency_code = #{currencyCode}")
    Optional<AccountBalance> getBalance(@Param("accountID") String accountID, @Param("currencyCode") String currencyCode);

    @Select("SELECT * FROM account_balance where account_id = #{accountId}")
    List<AccountBalance> getBalancesByAccountId(@Param("accountId") String accountId);
}
