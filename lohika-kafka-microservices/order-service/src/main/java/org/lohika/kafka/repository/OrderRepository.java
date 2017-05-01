package org.lohika.kafka.repository;

import org.lohika.kafka.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

    Order findByOrderUid(String orderUid);

}
