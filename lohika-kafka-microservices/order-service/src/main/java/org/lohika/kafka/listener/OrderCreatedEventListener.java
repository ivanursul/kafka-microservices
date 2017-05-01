package org.lohika.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.lohika.kafka.domain.Order;
import org.lohika.kafka.domain.OrderProduct;
import org.lohika.kafka.domain.OrderStatus;
import org.lohika.kafka.repository.OrderRepository;
import org.lohika.kafka.resource.OrderCreatedResource;
import org.lohika.kafka.resource.OrderProcessedResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@Slf4j
public class OrderCreatedEventListener {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    @Qualifier("orderProcessedKafkaTemplate")
    private KafkaTemplate<String, OrderProcessedResource> kafkaTemplate;

    @KafkaListener(topics = "order.created")
    public void orderCreated(
            @Payload(required = false) OrderCreatedResource value,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key
    ) {
        log.info("Listening to order.created.event: {}", value);

        Order order = convertCreatedEvent(key, value);
        orderRepository.save(order);

        kafkaTemplate.send(
               "order.processed",
                key,
                new OrderProcessedResource(
                        value.getLogin(),
                        value.getUid(),
                        value.getPrice(),
                        value.getProducts()
                )
        );
    }

    private Order convertCreatedEvent(String key, OrderCreatedResource value) {

        List<OrderProduct> orderProducts = value.getProducts().stream()
                .map(p -> new OrderProduct(p.getName(), p.getCount()))
                .collect(toList());

        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setOrderUid(key);
        order.setPrice(value.getPrice());
        order.setLogin(value.getLogin());
        order.setProducts(orderProducts);

        return order;
    }
}
