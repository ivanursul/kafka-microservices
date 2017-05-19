package org.kafka.microservices.customer.controller;

import lombok.extern.slf4j.Slf4j;
import org.kafka.microservices.customer.repository.CustomerRepository;
import org.kafka.microservices.customer.resource.ChargeAccountResource;
import org.kafka.microservices.customer.resource.CustomerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
public class AccountController {

    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<String, ChargeAccountResource> template;

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "/{login}", method = RequestMethod.POST)
    public void chargeAccount(
            @PathVariable("login") String login,
            @RequestBody ChargeAccountResource resource) {
        log.info("Creating user:{} recharge event: {}", login, resource);

        template.send("customer.account.charged", login, resource);
    }

    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    public CustomerResource customerInfo(@PathVariable("login") String login) {
        return customerRepository.findByLogin(login)
                .map(c -> new CustomerResource(c.getLogin(), c.getAmount()))
                .get();
    }

}
