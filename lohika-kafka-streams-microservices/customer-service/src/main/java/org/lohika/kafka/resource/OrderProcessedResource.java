package org.lohika.kafka.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProcessedResource {

    private String login;
    private String uid;
    private BigDecimal price;
    private BalanceStatus balanceStatus;

}
