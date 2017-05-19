package org.kafka.microservices.customer.resource;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChargeAccountResource {

    private BigDecimal amount;

}
