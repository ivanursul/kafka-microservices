package org.kafka.microservices.order.resource;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderResource {

    private String login;
    private String uid;
    private BigDecimal price;
    private List<OrderProductResource> products;

}
