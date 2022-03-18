package com.bankingsolution.account.query;

import com.bankingsolution.account.query.queries.FindAccountByIdQuery;
import com.bankingsolution.account.query.queries.FindAllAccountsQuery;
import com.bankingsolution.account.query.queries.QueryHandler;
import com.bankingsolution.cqrs.core.infrastructure.QueryDispatcher;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@MapperScan("com.bankingsolution.account.query.mappers")
public class QueryApplication {

	@Autowired
	private final QueryDispatcher queryDispatcher;

	@Autowired
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
