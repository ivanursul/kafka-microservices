package org.lohika.kafka.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Order {

    @Id
    private String id;

    private String orderUid;
    private String login;
    private BigDecimal price;
    private OrderStatus status;

    private List<OrderProduct> products;

}
