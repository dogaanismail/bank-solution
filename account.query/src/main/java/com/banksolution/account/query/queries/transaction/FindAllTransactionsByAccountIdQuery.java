package com.banksolution.account.query.queries.transaction;

import com.banksolution.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class FindAllTransactionsByAccountIdQuery extends BaseQuery {
    private String id;
}
