package com.bankingsolution.account.query.queries.transaction;

import com.bankingsolution.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAllTransactionsByAccountIdQuery extends BaseQuery {
    private String id;
}
