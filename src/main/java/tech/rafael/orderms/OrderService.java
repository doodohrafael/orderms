package tech.rafael.orderms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.rafael.orderms.controller.dto.OrderResponse;
import tech.rafael.orderms.entity.OrderEntity;
import tech.rafael.orderms.entity.OrderItem;
import tech.rafael.orderms.listener.dto.OrderCreatedEvent;
import tech.rafael.orderms.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public void save(OrderCreatedEvent event) {
        var entity = new OrderEntity();
        entity.setOrderId(event.orderId());
        entity.setCustomerId(event.customerId());
        entity.setItems(getOrderItems(event));
        entity.setTotal(getTotal(event));
        this.repository.save(entity);
    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
        var orders = this.repository.findAllByCustomerId(customerId, pageRequest);
        return orders.map(OrderResponse::fromEntity);
    }

    private static List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.items().stream().map(item ->
                new OrderItem(item.product(), item.quantity(), item.price())).toList();
    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.items().stream()
                .map(item -> (BigDecimal.valueOf(item.quantity()).multiply(item.price())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

}
