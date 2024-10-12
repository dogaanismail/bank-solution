package com.bankingsolution.account.query;

import com.bankingsolution.account.query.queries.accounting.FindAccountByIdQuery;
import com.bankingsolution.account.query.queries.accounting.FindAllAccountsQuery;
import com.bankingsolution.account.query.queries.accounting.IAccountQueryHandler;
import com.bankingsolution.account.query.queries.transaction.FindAllTransactionsByAccountIdQuery;
import com.bankingsolution.account.query.queries.transaction.ITransactionQueryHandler;
import com.bankingsolution.cqrs.core.infrastructure.QueryDispatcher;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@MapperScan("com.bankingsolution.account.query.mappers")
public class QueryApplication {

	private final QueryDispatcher queryDispatcher;
	private final IAccountQueryHandler accountQueryHandler;
	private final ITransactionQueryHandler transactionQueryHandler;

	public QueryApplication(QueryDispatcher queryDispatcher,
							IAccountQueryHandler accountQueryHandler,
							ITransactionQueryHandler transactionQueryHandler) {
		this.queryDispatcher = queryDispatcher;
		this.accountQueryHandler = accountQueryHandler;
		this.transactionQueryHandler = transactionQueryHandler;
	}

	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		queryDispatcher.registerHandler(FindAllAccountsQuery.class, accountQueryHandler::handle);
		queryDispatcher.registerHandler(FindAccountByIdQuery.class, accountQueryHandler::handle);
		queryDispatcher.registerHandler(FindAllTransactionsByAccountIdQuery.class, transactionQueryHandler::handle);
	}

}
