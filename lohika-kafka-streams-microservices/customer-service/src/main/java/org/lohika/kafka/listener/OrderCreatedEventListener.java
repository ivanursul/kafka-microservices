package org.lohika.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderCreatedEventListener {

/*    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    @Qualifier("withdrawStatusKafkaTemplate")
    private KafkaTemplate<String, WithdrawStatusResource> kafkaTemplate;

    @KafkaListener(topics = "order.processed")
    @Transactional
    public void orderCreatedEvent(
            @Payload(required = false) OrderProcessedResource value,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key
    ) {
        log.info("Withdrawing from user's account: {}", value);

        int recordsAffected = customerRepository.withdrawAmount(value.getLogin(), value.getPrice());

        WithdrawStatus status = recordsAffected == 0 ?
                WithdrawStatus.FAILED : WithdrawStatus.WITHDRAWN;

        kafkaTemplate.send(
                "order.payment.processed",
                key,
                new WithdrawStatusResource(
                        value.getLogin(),
                        status
                )
        );

    }*/
}
