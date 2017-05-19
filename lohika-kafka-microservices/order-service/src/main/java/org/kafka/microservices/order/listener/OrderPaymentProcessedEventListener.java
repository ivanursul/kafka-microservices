package org.kafka.microservices.order.listener;

import lombok.extern.slf4j.Slf4j;
import org.kafka.microservices.order.domain.Order;
import org.kafka.microservices.order.domain.OrderStatus;
import org.kafka.microservices.order.repository.OrderRepository;
import org.kafka.microservices.order.resource.PaymentProcessedResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderPaymentProcessedEventListener {

    @Autowired
    private OrderRepository orderRepository;

    @KafkaListener(topics = "order.payment.processed")
    public void orderCreated(
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
            @Payload PaymentProcessedResource value) {
        log.info("Received withdrawn event: {}", value);

        OrderStatus orderStatus = OrderStatus.fromPaymentStatus(value.getStatus());

        updateOrderStatus(key, orderStatus);
    }

    private void updateOrderStatus(String orderUid, OrderStatus orderStatus) {
        Order order = orderRepository.findByOrderUid(orderUid);
        order.setStatus(orderStatus);

        orderRepository.save(order);
    }

}
