package org.lohika.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.lohika.kafka.domain.Order;
import org.lohika.kafka.domain.OrderStatus;
import org.lohika.kafka.repository.OrderRepository;
import org.lohika.kafka.resource.WithdrawStatus;
import org.lohika.kafka.resource.WithdrawStatusResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WithdrawStatusEventListener {

    @Autowired
    private OrderRepository orderRepository;

    @KafkaListener(topics = "order.payment.processed")
    public void orderCreated(
            @Payload(required = false) WithdrawStatusResource value,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key
    ) {
        log.info("Received withdrawn event: {}", value);
        Order order = orderRepository.findByOrderUid(key);

        if (WithdrawStatus.WITHDRAWN.equals(value.getStatus())) {
            order.setStatus(OrderStatus.PAYED);
        } else {
            order.setStatus(OrderStatus.PAYMENT_FAILED);
        }

        orderRepository.save(order);
    }

}
