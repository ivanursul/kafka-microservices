package org.kafka.microservices.order.domain;

import org.kafka.microservices.order.resource.PaymentOperationsStatus;

public enum OrderStatus {

    CREATED, PAYED, PAYMENT_FAILED;

    public static OrderStatus fromPaymentStatus(PaymentOperationsStatus status) {
        return PaymentOperationsStatus.PAYED.equals(status) ? PAYED : PAYMENT_FAILED;
    }
}
