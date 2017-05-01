package org.lohika.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderCreatedEventListener {
/*
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
    }*/
}
