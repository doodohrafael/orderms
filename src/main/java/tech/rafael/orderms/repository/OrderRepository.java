package tech.rafael.orderms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.rafael.orderms.entity.OrderEntity;

public interface OrderRepository extends MongoRepository<OrderEntity, Long> {
}
