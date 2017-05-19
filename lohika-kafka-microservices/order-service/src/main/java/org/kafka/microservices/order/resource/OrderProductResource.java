package org.kafka.microservices.order.resource;

import lombok.Data;

@Data
public class OrderProductResource {

    private String name;
    private Integer count;

}
