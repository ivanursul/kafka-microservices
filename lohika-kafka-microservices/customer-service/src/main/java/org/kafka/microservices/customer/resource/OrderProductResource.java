package org.kafka.microservices.customer.resource;

import lombok.Data;

@Data
public class OrderProductResource {

    private String name;
    private Integer count;

}
