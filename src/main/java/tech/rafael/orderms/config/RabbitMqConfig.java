package tech.rafael.orderms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String ORDER_CREATED = "order-created";
    public static final Logger logger = LoggerFactory.getLogger(RabbitMqConfig.class);

    @Bean
    public JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        logger.info("Json converted");
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public Declarable orderCreatedQueue() {
        return new Queue(ORDER_CREATED);
    }

}
