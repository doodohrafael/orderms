package tech.rafael.orderms.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import tech.rafael.orderms.OrderService;
import tech.rafael.orderms.listener.dto.OrderCreatedEvent;

import static tech.rafael.orderms.config.RabbitMqConfig.ORDER_CREATED;

@Component
public class OrderCreatedListener {

    public static final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final OrderService orderService;

    public OrderCreatedListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = ORDER_CREATED)
    public void listen(Message<OrderCreatedEvent> message) {
        logger.info("Message consumed: {}", message);
        this.orderService.save(message.getPayload());
    }

}
