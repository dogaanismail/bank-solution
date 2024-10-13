package com.banksolution.account.cmd.configuration;

import com.banksolution.common.constants.Constants;
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
        return new Queue(Constants.ACCOUNT_OPENED_EVENT_TOPIC);
    }

    @Bean
    Queue fundsDepositedQueue() {
        return new Queue(Constants.FUNDS_DEPOSITED_EVENT_TOPIC);
    }

    @Bean
    Queue fundsWithdrawnQueue() {
        return new Queue(Constants.FUNDS_WITH_DRAWN_EVENT_TOPIC);
    }

    @Bean
    Queue transactionCreatedQueue() {
        return new Queue(Constants.TRANSACTION_CREATED_EVENT_TOPIC);
    }

    @Bean
    Queue transactionFailedQueue() {
        return new Queue(Constants.TRANSACTION_FAILED_EVENT_TOPIC);
    }

    @Bean
    Binding accountOpened(TopicExchange exchange) {
        return BindingBuilder.bind(accountOpenedQueue()).to(exchange).with(Constants.ACCOUNT_OPENED_EVENT_TOPIC);
    }

    @Bean
    Binding fundsDeposited(TopicExchange exchange) {
        return BindingBuilder.bind(fundsDepositedQueue()).to(exchange).with(Constants.FUNDS_DEPOSITED_EVENT_TOPIC);
    }

    @Bean
    Binding fundsWithdrawn(TopicExchange exchange) {
        return BindingBuilder.bind(fundsWithdrawnQueue()).to(exchange).with(Constants.FUNDS_DEPOSITED_EVENT_TOPIC);
    }

    @Bean
    Binding transactionCreated(TopicExchange exchange) {
        return BindingBuilder.bind(transactionCreatedQueue()).to(exchange).with(Constants.TRANSACTION_CREATED_EVENT_TOPIC);
    }

    @Bean
    Binding transactionFailed(TopicExchange exchange) {
        return BindingBuilder.bind(transactionFailedQueue()).to(exchange).with(Constants.TRANSACTION_FAILED_EVENT_TOPIC);
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
