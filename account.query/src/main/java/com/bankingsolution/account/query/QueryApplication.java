package com.bankingsolution.account.query;

import com.bankingsolution.account.query.queries.FindAccountByIdQuery;
import com.bankingsolution.account.query.queries.FindAllAccountsQuery;
import com.bankingsolution.account.query.queries.IAccountQueryHandler;
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
	private final IAccountQueryHandler IAccountQueryHandler;

	public QueryApplication(QueryDispatcher queryDispatcher,
							IAccountQueryHandler IAccountQueryHandler) {
		this.queryDispatcher = queryDispatcher;
		this.IAccountQueryHandler = IAccountQueryHandler;
	}

	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		queryDispatcher.registerHandler(FindAllAccountsQuery.class, IAccountQueryHandler::handle);
		queryDispatcher.registerHandler(FindAccountByIdQuery.class, IAccountQueryHandler::handle);
	}

}
