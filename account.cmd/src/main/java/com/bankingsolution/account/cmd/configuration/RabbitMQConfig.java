package com.bankingsolution.account.cmd.configuration;

import com.bankingsolution.common.constants.Contants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(Contants.TOPIC_KEY);
    }

    @Bean
    Queue accountOpenedQueue() {
        return new Queue(Contants.AccountOpenedTopic);
    }

    @Bean
    Queue fundsDepositedQueue() {
        return new Queue(Contants.FundsDepositedTopic);
    }

    @Bean
    Queue fundsWithdrawnQueue() {
        return new Queue(Contants.FundsWithDrawnTopic);
    }

    @Bean
    Queue transactionCreatedQueue() {
        return new Queue(Contants.TransactionCreatedTopic);
    }

    @Bean
    Queue transactionFailedQueue() {
        return new Queue(Contants.TransactionFailedTopic);
    }

    @Bean
    Binding accountOpened(TopicExchange exchange) {
        return BindingBuilder.bind(accountOpenedQueue()).to(exchange).with(Contants.AccountOpenedTopic);
    }

    @Bean
    Binding fundsDeposited(TopicExchange exchange) {
        return BindingBuilder.bind(fundsDepositedQueue()).to(exchange).with(Contants.FundsDepositedTopic);
    }

    @Bean
    Binding fundsWithdrawn(TopicExchange exchange) {
        return BindingBuilder.bind(fundsWithdrawnQueue()).to(exchange).with(Contants.FundsDepositedTopic);
    }

    @Bean
    Binding transactionCreated(TopicExchange exchange) {
        return BindingBuilder.bind(transactionCreatedQueue()).to(exchange).with(Contants.TransactionCreatedTopic);
    }

    @Bean
    Binding transactionFailed(TopicExchange exchange) {
        return BindingBuilder.bind(transactionFailedQueue()).to(exchange).with(Contants.TransactionFailedTopic);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
