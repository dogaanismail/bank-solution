package com.bankingsolution.customer.rabbitmq.config;

import com.bankingsolution.common.constants.CommonContants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(CommonContants.TOPIC_KEY);
    }

    @Bean
    Queue customerQueue() {
        return new Queue(CommonContants.CUSTOMER_KEY);
    }

    @Bean
    Binding bindingCustomer(TopicExchange exchange) {
        return BindingBuilder.bind(customerQueue()).to(exchange).with(CommonContants.CUSTOMER_KEY);
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
