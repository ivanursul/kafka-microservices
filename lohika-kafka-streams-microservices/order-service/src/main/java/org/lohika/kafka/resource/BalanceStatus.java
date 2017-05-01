package org.lohika.kafka.resource;

import java.math.BigDecimal;

public enum BalanceStatus {

    AVAILABLE,
    UNAVAILABLE;

    public static BalanceStatus calculate(BigDecimal balance, BigDecimal orderPrice) {
        return balance.subtract(orderPrice).compareTo(BigDecimal.ZERO) > 0 ? AVAILABLE : UNAVAILABLE;
    }

}
