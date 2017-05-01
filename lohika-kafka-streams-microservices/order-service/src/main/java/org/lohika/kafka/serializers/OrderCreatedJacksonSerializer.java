package org.lohika.kafka.serializers;

import org.lohika.kafka.BaseJacksonSerializer;
import org.lohika.kafka.resource.OrderCreatedResource;

public class OrderCreatedJacksonSerializer extends BaseJacksonSerializer<OrderCreatedResource> {

    @Override
    public Class<OrderCreatedResource> getClz() {
        return OrderCreatedResource.class;
    }
}
