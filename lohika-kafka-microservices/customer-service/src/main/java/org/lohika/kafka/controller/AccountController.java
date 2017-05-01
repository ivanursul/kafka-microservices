package org.lohika.kafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.lohika.kafka.resource.ChargeAccountResource;
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

    @RequestMapping(value = "/{login}", method = RequestMethod.POST)
    public void chargeAccount(
            @PathVariable("login") String login,
            @RequestBody ChargeAccountResource resource) {
        log.info("Creating user:{} recharge event: {}", login, resource);

        template.send("login.account.charged", login, resource);
    }

}
