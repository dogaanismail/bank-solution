package com.bankingsolution.account.query.configuration;

import com.bankingsolution.common.constants.CommonContants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.Arrays;
import java.util.List;

@Configuration
public class RabbitMQConfig implements RabbitListenerConfigurer {
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
    public List<Binding> binding() {
        return Arrays.asList(
                BindingBuilder.bind(accountOpenedQueue()).to(exchange()).with(CommonContants.AccountOpenedTopic),
                BindingBuilder.bind(fundsDepositedQueue()).to(exchange()).with(CommonContants.FundsDepositedTopic),
                BindingBuilder.bind(fundsWithdrawnQueue()).to(exchange()).with(CommonContants.FundsWithDrawnTopic),
                BindingBuilder.bind(transactionCreatedQueue()).to(exchange()).with(CommonContants.TransactionCreatedTopic));
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Autowired
    public ConnectionFactory connectionFactory;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(4);
        factory.setMaxConcurrentConsumers(20);
        return factory;
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
