package org.lohika.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChargeAccountEventListener {

/*    @Autowired
    private CustomerRepository customerRepository;

    @KafkaListener(topics = "login.account.charged")
    @Transactional
    public void chargeAccountEvent(
            @Payload(required = false) ChargeAccountResource value,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key
    ) {
        log.info("User: {} has charged his account: {}", key, value);
        customerRepository.updateBalance(key, value.getAmount());
    }*/
}
