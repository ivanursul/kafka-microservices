package org.lohika.kafka.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBalance {

    private String login;
    private String uid;
    private BigDecimal amount;
    private BigDecimal orderPrice;

}
