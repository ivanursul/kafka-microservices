package org.kafka.microservices.customer.serializers;

import org.lohika.kafka.BaseJacksonSerializer;
import org.kafka.microservices.customer.resource.PaymentProcessedResource;

public class WithdrawStatusJacksonSerializer extends BaseJacksonSerializer<PaymentProcessedResource> {

    @Override
    public Class<PaymentProcessedResource> getClz() {
        return PaymentProcessedResource.class;
    }
}
