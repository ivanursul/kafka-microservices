package org.kafka.microservices.customer.serializers;

import org.lohika.kafka.BaseJacksonSerializer;
import org.kafka.microservices.customer.resource.ChargeAccountResource;

public class AccountChargeJacksonSerializer extends BaseJacksonSerializer<ChargeAccountResource> {

    @Override
    public Class<ChargeAccountResource> getClz() {
        return ChargeAccountResource.class;
    }
}
