package org.kafka.microservices.customer.listener;

import lombok.extern.slf4j.Slf4j;
import org.kafka.microservices.customer.repository.CustomerRepository;
import org.kafka.microservices.customer.resource.OrderProcessedResource;
import org.kafka.microservices.customer.resource.PaymentOperationStatus;
import org.kafka.microservices.customer.resource.PaymentProcessedResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class OrderProcessedEventListener {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    @Qualifier("withdrawStatusKafkaTemplate")
    private KafkaTemplate<String, PaymentProcessedResource> kafkaTemplate;

    @KafkaListener(topics = "order.processed")
    @Transactional
    public void orderProcessedEvent(
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
            @Payload OrderProcessedResource value) {
        log.info("Withdrawing from user's account: {}", value);

        boolean result = customerRepository.withdrawAmount(value.getLogin(), value.getPrice()) > 0;

        PaymentProcessedResource resource = new PaymentProcessedResource(
                value.getLogin(),
                PaymentOperationStatus.fromResult(result)
        );

        kafkaTemplate.send(
                "order.payment.processed",
                key,
                resource
        );
    }
}
