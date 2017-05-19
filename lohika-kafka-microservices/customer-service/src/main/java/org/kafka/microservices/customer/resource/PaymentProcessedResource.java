package org.kafka.microservices.customer.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessedResource {

    private String login;
    private PaymentOperationStatus status;

}
