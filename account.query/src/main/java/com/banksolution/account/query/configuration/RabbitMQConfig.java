package com.banksolution.account.query.configuration;

import com.banksolution.common.constants.Constants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.Arrays;
import java.util.List;

@Configuration
public class RabbitMQConfig implements RabbitListenerConfigurer {

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
    public List<Binding> binding() {
        return Arrays.asList(
                BindingBuilder.bind(accountOpenedQueue()).to(exchange()).with(Constants.ACCOUNT_OPENED_EVENT_TOPIC),
                BindingBuilder.bind(fundsDepositedQueue()).to(exchange()).with(Constants.FUNDS_DEPOSITED_EVENT_TOPIC),
                BindingBuilder.bind(fundsWithdrawnQueue()).to(exchange()).with(Constants.FUNDS_WITH_DRAWN_EVENT_TOPIC),
                BindingBuilder.bind(transactionCreatedQueue()).to(exchange()).with(Constants.TRANSACTION_CREATED_EVENT_TOPIC),
                BindingBuilder.bind(transactionFailedQueue()).to(exchange()).with(Constants.TRANSACTION_FAILED_EVENT_TOPIC));
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory());
        return factory;
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


    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }
}
