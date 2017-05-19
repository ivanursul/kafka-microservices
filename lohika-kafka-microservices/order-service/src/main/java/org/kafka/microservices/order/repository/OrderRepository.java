package org.kafka.microservices.order.repository;

import org.kafka.microservices.order.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

    Order findByOrderUid(String orderUid);

}
