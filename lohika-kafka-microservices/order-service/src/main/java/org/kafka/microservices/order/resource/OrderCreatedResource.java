package org.kafka.microservices.order.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedResource {

    private String login;
    private String uid;
    private BigDecimal price;
    private List<OrderProductResource> products;

}
