package com.banksolution.account.query;

import com.banksolution.account.query.queries.accounting.FindAccountByIdQuery;
import com.banksolution.account.query.queries.accounting.FindAllAccountsQuery;
import com.banksolution.account.query.queries.accounting.IAccountQueryHandler;
import com.banksolution.account.query.queries.transaction.FindAllTransactionsByAccountIdQuery;
import com.banksolution.account.query.queries.transaction.ITransactionQueryHandler;
import com.banksolution.cqrs.core.infrastructure.QueryDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
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
