package org.kafka.microservices.customer.resource;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CustomerResource {

    private String login;

    private BigDecimal amount;

}
