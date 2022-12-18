package com.bankingsolution.account.cmd.configuration;

import com.bankingsolution.common.constants.Constants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String hostName;

    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(Constants.TOPIC_KEY);
    }

    @Bean
    Queue accountOpenedQueue() {
        return new Queue(Constants.AccountOpenedTopic);
    }

    @Bean
    Queue fundsDepositedQueue() {
        return new Queue(Constants.FundsDepositedTopic);
    }

    @Bean
    Queue fundsWithdrawnQueue() {
        return new Queue(Constants.FundsWithDrawnTopic);
    }

    @Bean
    Queue transactionCreatedQueue() {
        return new Queue(Constants.TransactionCreatedTopic);
    }

    @Bean
    Queue transactionFailedQueue() {
        return new Queue(Constants.TransactionFailedTopic);
    }

    @Bean
    Binding accountOpened(TopicExchange exchange) {
        return BindingBuilder.bind(accountOpenedQueue()).to(exchange).with(Constants.AccountOpenedTopic);
    }

    @Bean
    Binding fundsDeposited(TopicExchange exchange) {
        return BindingBuilder.bind(fundsDepositedQueue()).to(exchange).with(Constants.FundsDepositedTopic);
    }

    @Bean
    Binding fundsWithdrawn(TopicExchange exchange) {
        return BindingBuilder.bind(fundsWithdrawnQueue()).to(exchange).with(Constants.FundsDepositedTopic);
    }

    @Bean
    Binding transactionCreated(TopicExchange exchange) {
        return BindingBuilder.bind(transactionCreatedQueue()).to(exchange).with(Constants.TransactionCreatedTopic);
    }

    @Bean
    Binding transactionFailed(TopicExchange exchange) {
        return BindingBuilder.bind(transactionFailedQueue()).to(exchange).with(Constants.TransactionFailedTopic);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(this.hostName);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername(this.userName);
        connectionFactory.setPassword(this.password);
        connectionFactory.setPort(this.port);
        return connectionFactory;
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
