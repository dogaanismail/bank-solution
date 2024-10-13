package com.banksolution.account.query.mappers;

import com.banksolution.account.query.domain.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {

    @Insert("INSERT INTO account(account_id, customer_id, country) VALUES"
            + "(#{accountId}, #{customerId}, #{country})")
    @Options(useGeneratedKeys = true, keyProperty = "accountId", keyColumn = "account_id")
    void insertAccount(Account account);

    @Select("SELECT account_id as AccountId, customer_id as CustomerId, country as Country FROM account")
    List<Account> getAllAccounts();

    @Select("SELECT * FROM account WHERE account_id= #{accountId}")
    @Results(id = "accountResult", value = {
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "customerId", column = "customer_id"),
    })
    Account getAccountById(@Param("accountId") String accountId);
}
