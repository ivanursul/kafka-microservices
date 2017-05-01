package org.lohika.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WithdrawStatusEventListener {
/*

    @Autowired
    private OrderRepository orderRepository;

    @KafkaListener(topics = "order.payment.processed")
    public void orderCreated(
            @Payload(required = false) WithdrawStatusResource value,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key
    ) {
        log.info("Received withdrawn event: {}", value);
        Order order = orderRepository.findByOrderUid(key);

        if (WithdrawStatus.AVAILABLE.equals(value.getStatus())) {
            order.setStatus(OrderStatus.PAYED);
        } else {
            order.setStatus(OrderStatus.PAYMENT_FAILED);
        }

        orderRepository.save(order);
    }
*/

}
