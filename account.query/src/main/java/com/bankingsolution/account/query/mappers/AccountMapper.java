package com.bankingsolution.account.query.mappers;

import com.bankingsolution.account.query.domain.Account;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AccountMapper {

    @Insert("INSERT INTO account(account_id, customer_id, country) VALUES"
            + "(#{accountId}, #{customerId}, #{country})")
    @Options(useGeneratedKeys = true, keyProperty = "accountId", keyColumn = "account_id")
    public void insertAccount(Account account);

    @Select("SELECT account_id as AccountId, customer_id as CustomerId, country as Country FROM account")
    public List<Account> getAllAccounts();

    @Select("SELECT account_id as AccountId, customer_id as CustomerId FROM account WHERE account_id= #{accountId}")
    @Results(id = "accountResult", value = {
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "customerId", column = "customer_id"),
    })
    public Optional<Account> getAccountById(@Param("customerId") String accountId);

    @Select("SELECT account_id as AccountId, customer_id as CustomerId FROM account WHERE customer_id= #{customerId}")
    @ResultMap("accountResult")
    public Long getAccountIdByCustomerId(@Param("customerId") Long customerId);
}
