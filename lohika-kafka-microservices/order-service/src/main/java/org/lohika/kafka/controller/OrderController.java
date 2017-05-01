package org.lohika.kafka.controller;

import org.lohika.kafka.domain.Order;
import org.lohika.kafka.repository.OrderRepository;
import org.lohika.kafka.resource.OrderCreatedResource;
import org.lohika.kafka.resource.OrderResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private KafkaTemplate<String, OrderCreatedResource> kafkaTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(method = RequestMethod.POST)
    public String createOrder(@RequestBody OrderResource resource) {
        logger.info("Creating resource: {}", resource);

        String orderUid = UUID.randomUUID().toString();

        kafkaTemplate.send(
                "order.created",
                orderUid,
                new OrderCreatedResource(
                        resource.getLogin(),
                        orderUid,
                        resource.getPrice(),
                        resource.getProducts()
                )
        );

        return orderUid;
    }

    @RequestMapping(value = "/{orderUid}", method = RequestMethod.GET)
    public Order getOrder(@PathVariable("orderUid") String orderUid) {
        return orderRepository.findByOrderUid(orderUid);
    }
}
