package com.bankingsolution.account.cmd.configuration;

import com.bankingsolution.common.constants.CommonContants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(CommonContants.TOPIC_KEY);
    }

    @Bean
    Queue accountOpenedQueue() {
        return new Queue(CommonContants.AccountOpenedTopic);
    }

    @Bean
    Queue fundsDepositedQueue() {
        return new Queue(CommonContants.FundsDepositedTopic);
    }

    @Bean
    Queue fundsWithdrawnQueue() {
        return new Queue(CommonContants.FundsWithDrawnTopic);
    }

    @Bean
    Queue transactionCreatedQueue() {
        return new Queue(CommonContants.TransactionCreatedTopic);
    }

    @Bean
    Binding accountOpened(TopicExchange exchange) {
        return BindingBuilder.bind(accountOpenedQueue()).to(exchange).with(CommonContants.AccountOpenedTopic);
    }

    @Bean
    Binding fundsDeposited(TopicExchange exchange) {
        return BindingBuilder.bind(fundsDepositedQueue()).to(exchange).with(CommonContants.FundsDepositedTopic);
    }

    @Bean
    Binding fundsWithdrawn(TopicExchange exchange) {
        return BindingBuilder.bind(fundsWithdrawnQueue()).to(exchange).with(CommonContants.FundsDepositedTopic);
    }

    @Bean
    Binding transactionCreated(TopicExchange exchange) {
        return BindingBuilder.bind(transactionCreatedQueue()).to(exchange).with(CommonContants.TransactionCreatedTopic);
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
