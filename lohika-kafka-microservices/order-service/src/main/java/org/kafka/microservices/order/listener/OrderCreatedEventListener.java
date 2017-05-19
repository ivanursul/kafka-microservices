package org.kafka.microservices.order.listener;

import lombok.extern.slf4j.Slf4j;
import org.kafka.microservices.order.converter.OrderCreatedResourceConverter;
import org.kafka.microservices.order.domain.Order;
import org.kafka.microservices.order.repository.OrderRepository;
import org.kafka.microservices.order.resource.OrderCreatedResource;
import org.kafka.microservices.order.resource.OrderProcessedResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderCreatedEventListener {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderCreatedResourceConverter resourceConverter;

    @Autowired
    @Qualifier("orderProcessedKafkaTemplate")
    private KafkaTemplate<String, OrderProcessedResource> kafkaTemplate;

    @KafkaListener(topics = "order.created")
    public void orderCreated(
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
            @Payload OrderCreatedResource value) {
        log.info("Listening to order.created.event: {}", value);

        Order order = resourceConverter.convert(value);
        orderRepository.save(order);

        kafkaTemplate.send(
                "order.processed",
                key,
                new OrderProcessedResource(
                        value.getLogin(), value.getUid(), value.getPrice(), value.getProducts()
                )
        );
    }
}
