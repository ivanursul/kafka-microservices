package org.lohika.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.lohika.kafka.repository.CustomerRepository;
import org.lohika.kafka.resource.ChargeAccountResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class ChargeAccountEventListener {

    @Autowired
    private CustomerRepository customerRepository;

    @KafkaListener(topics = "login.account.charged")
    @Transactional
    public void chargeAccountEvent(
            @Payload(required = false) ChargeAccountResource value,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key
    ) {
        log.info("User: {} has charged his account: {}", key, value);
        customerRepository.updateBalance(key, value.getAmount());
    }
}
