# Simple Core Banking Solution
A simple core banking solution with microservices using Domain Driven Design and Event Sourcing.

According with **[Martin Folwer]**(https://martinfowler.com/bliki/CQRS.html) 
> At its heart is the notion that you can use a different model to update information than the model you use to read information. 
> For some situations, this separation can be valuable, but beware that for most systems CQRS adds risky complexity.

## There are three microservices:

- **Account Query Service** : This microservice is responsible for read information about the accounts and transactions using the read database.
- **Account Command Service** : This microservice is responsible for managing accounts and transactions. A user can open an account, deposit funds and withdraw funds.
- **Customer Service Service** : This microservice is responsible for managing customers. There are some operations, such as creating a customer, list of all customers, find customer by Id.

### Concepts used ###
- Event-Driven Microservices
- CQRS Design Pattern
- Event Based Messages
- Domain Driven Design
- RabbitMQ
- NoSQL database with MongoDB
- Postgres
- JUnit
- SpringBoot
- MyBatis
- Gradle

# Requirements & Deployment
[Docker Desktop](http://www.docker.io/gettingstarted/#h_installation) has to be installed and to be run. 

**Step 1:**  Clone the repo

```
git clone https://github.com/dogaanismail/bank-solution.git
```

**Step 2:** Open IDE that can build Spring Boot project (Ex: Netbeans, Eclipse, Intellij IDEA,
etc.)

**Step 3:** Gradle will sync the project and it takes a few minutes<br />

**Step 4:** Clean and build the project by using gradle from IDE or terminal

* gradlew clean
* gradlew build

**Step 5:** Go inside the project directory from command prompt terminal

```
cd bank-solution
```

**Step 6:** Start the docker containers using the following command

```
docker-compose up -d
```

The process will take a few minutes. 7 containers must be started.

* **account-query-service**
* **account-cmd-service**
* **customer-service**
* **customerdb**
* **bank_rabbitmq**
* **querydb**
* **cmddb**

**Step 7:** If there is not problem, you must be able to access Rest API by using Swagger

# Swagger UI links #
* **Customer Service: http://localhost:5000/swagger-ui.html**
* **Account Command Service: http://localhost:5002/swagger-ui.html**
* **Account Query Service: http://localhost:5003/swagger-ui.html**

### EndPoints ###

| Service         | EndPoint                                       | Method | Description           |
|-----------------|------------------------------------------------|:------:|-----------------------|
| Account Command | /api/v1/account/openAccount                    |  POST  | Open an account       |
| Account Command | /api/v1/transaction/createTransaction          |  POST  | Create a transaction  |
| Account Query   | /api/v1/account/getAccounts                    |  GET   | List of accounts      |
| Account Query   | /api/v1/transaction/getTransactions/{accountId}|  GET   | Account by Id         |
| Customer Service| /api/v1/customer/createCustomer                |  POST  | Create a customer     |
| Customer Service| /api/v1/customer/get/{customerId}              |  GET   | Get by Id             |
| Customer Service| /api/v1/customer/getCustomers                  |  GET   | List of customers     |


## Important Choices

Financial sector is very serious and there must not be any mistake. Every transaction should be recorded tightly and data loss should be minimized.

* Working with micro services
* Implementations of Doman Driven Design
* Implementations CQRS Design Pattern
* Implementations Event Based Messages, such as FundsDepositedEvent, FundsWithDrawnEvent, TransactionCreatedEvent etc.


