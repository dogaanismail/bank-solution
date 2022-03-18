package com.bankingsolution.account.query;

import com.bankingsolution.account.query.queries.FindAccountByIdQuery;
import com.bankingsolution.account.query.queries.FindAllAccountsQuery;
import com.bankingsolution.account.query.queries.QueryHandler;
import com.bankingsolution.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class QueryApplication {

	private final QueryDispatcher queryDispatcher;
	private final QueryHandler queryHandler;

	public QueryApplication(QueryDispatcher queryDispatcher,
							QueryHandler queryHandler) {
		this.queryDispatcher = queryDispatcher;
		this.queryHandler = queryHandler;
	}

	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		queryDispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler::handle);
	}

}
