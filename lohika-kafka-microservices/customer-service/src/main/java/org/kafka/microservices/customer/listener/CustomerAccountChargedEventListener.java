package org.kafka.microservices.customer.listener;

import lombok.extern.slf4j.Slf4j;
import org.kafka.microservices.customer.repository.CustomerRepository;
import org.kafka.microservices.customer.resource.ChargeAccountResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class CustomerAccountChargedEventListener {

    @Autowired
    private CustomerRepository customerRepository;

    @KafkaListener(topics = "customer.account.charged")
    @Transactional
    public void chargeAccountEvent(
            @Payload(required = false) ChargeAccountResource value,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key
    ) {
        log.info("User: {} has charged his account: {}", key, value);
        customerRepository.updateBalance(key, value.getAmount());
    }
}
